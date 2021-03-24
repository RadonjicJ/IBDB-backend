package com.ibdbcompany.ibdb.web.rest;

import com.ibdbcompany.ibdb.domain.Quote;
import com.ibdbcompany.ibdb.service.QuoteService;
import com.ibdbcompany.ibdb.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ibdbcompany.ibdb.domain.Quote}.
 */
@RestController
@RequestMapping("/api")
public class QuoteResource {

    private final Logger log = LoggerFactory.getLogger(QuoteResource.class);

    private static final String ENTITY_NAME = "quote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuoteService quoteService;

    public QuoteResource(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    /**
     * {@code POST  /quotes} : Create a new quote.
     *
     * @param quote the quote to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quote, or with status {@code 400 (Bad Request)} if the quote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/quotes")
    public ResponseEntity<Quote> createQuote(@Valid @RequestBody Quote quote) throws URISyntaxException {
        log.debug("REST request to save Quote : {}", quote);
        if (quote.getId() != null) {
            throw new BadRequestAlertException("A new quote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Quote result = quoteService.save(quote);
        return ResponseEntity.created(new URI("/api/quotes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /quotes} : Updates an existing quote.
     *
     * @param quote the quote to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quote,
     * or with status {@code 400 (Bad Request)} if the quote is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quote couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/quotes")
    public ResponseEntity<Quote> updateQuote(@Valid @RequestBody Quote quote) throws URISyntaxException {
        log.debug("REST request to update Quote : {}", quote);
        if (quote.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Quote result = quoteService.save(quote);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, quote.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /quotes} : get all the quotes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quotes in body.
     */
    @GetMapping("/quotes")
    public ResponseEntity<List<Quote>> getAllQuotes(Pageable pageable) {
        log.debug("REST request to get a page of Quotes");
        Page<Quote> page = quoteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /quotes/:id} : get the "id" quote.
     *
     * @param id the id of the quote to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quote, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quotes/{id}")
    public ResponseEntity<Quote> getQuote(@PathVariable Long id) {
        log.debug("REST request to get Quote : {}", id);
        Optional<Quote> quote = quoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(quote);
    }

    /**
     * {@code DELETE  /quotes/:id} : delete the "id" quote.
     *
     * @param id the id of the quote to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/quotes/{id}")
    public ResponseEntity<Void> deleteQuote(@PathVariable Long id) {
        log.debug("REST request to delete Quote : {}", id);
        quoteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
