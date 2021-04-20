package com.ibdbcompany.ibdb.service;

import com.ibdbcompany.ibdb.domain.Quote;
import com.ibdbcompany.ibdb.repository.QuoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Quote}.
 */
@Service
@Transactional
public class QuoteService {

    private final Logger log = LoggerFactory.getLogger(QuoteService.class);

    private final QuoteRepository quoteRepository;

    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    /**
     * Save a quote.
     *
     * @param quote the entity to save.
     * @return the persisted entity.
     */
    public Quote save(Quote quote) {
        log.debug("Request to save Quote : {}", quote);
        return quoteRepository.save(quote);
    }

    /**
     * Get all the quotes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Quote> findAll(Pageable pageable) {
        log.debug("Request to get all Quotes");
        return quoteRepository.findAll(pageable);
    }


    /**
     * Get one quote by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Quote> findOne(Long id) {
        log.debug("Request to get Quote : {}", id);
        return quoteRepository.findById(id);
    }

    /**
     * Delete the quote by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Quote : {}", id);
        quoteRepository.deleteById(id);
    }

    /**
     *
     * @param id
     * @param pageable
     * @return
     */
    public Page<Quote> findAllQuotesByBookId(Long id, Pageable pageable){
        log.debug("Request to get Quotes by book id: {}", id);
        return quoteRepository.findQuotesByBookId(id, pageable);
    }

    public Page<Quote> findAllQuotesByAuthorId(Long id, Pageable pageable){
        log.debug("Request to get Quotes by author id: {}", id);
        return quoteRepository.findQuotesByAuthorId(id, pageable);
    }
}
