package com.ibdbcompany.ibdb.web.rest;

import com.ibdbcompany.ibdb.domain.Comment;
import com.ibdbcompany.ibdb.domain.UserBook;
import com.ibdbcompany.ibdb.service.UserBookService;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserBookResource {

    private final Logger log = LoggerFactory.getLogger(UserBookResource.class);

    private final static String ENTITY_NAME = "user_book";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserBookService userBookService;

    public UserBookResource(UserBookService userBookService) {
        this.userBookService = userBookService;
    }


    /**
     * {@code POST  /userbooks} : Create a new userBook.
     *
     * @param userBook the userBook to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userBook, or with status {@code 400 (Bad Request)} if the userBook has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/userbooks")
    public ResponseEntity<UserBook> createUserBook(@Valid @RequestBody UserBook userBook) throws URISyntaxException {
        log.debug("REST request to save UserBook : {}", userBook);
        if (userBook.getId() != null && userBook.getId()!=0) {
            throw new BadRequestAlertException("A new userBook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        userBook.setId(null);

        UserBook result = userBookService.save(userBook);
        return ResponseEntity.created(new URI("/api/userbooks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /userbooks} : Updates an existing userBook.
     *
     * @param userBook the userBook to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userBook,
     * or with status {@code 400 (Bad Request)} if the userBook is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userBook couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/userbooks")
    public ResponseEntity<UserBook> updateUserBook(@Valid @RequestBody UserBook userBook) throws URISyntaxException {
        log.debug("REST request to update UserBook : {}", userBook);
        if (userBook.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserBook result = userBookService.save(userBook);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userBook.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /userbooks} : get all the userBooks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userBook in body.
     */
    @GetMapping("/userbooks")
    public ResponseEntity<List<UserBook>> getAllUserBook(Pageable pageable) {
        log.debug("REST request to get a page of UserBook");
        Page<UserBook> page = userBookService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /userbooks/:id} : get the "id" userBook.
     *
     * @param id the id of the userBook to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userBook, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/userbooks/{id}")
    public ResponseEntity<UserBook> getUserBook(@PathVariable Long id) {
        log.debug("REST request to get UserBook : {}", id);
        Optional<UserBook> userBook = userBookService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userBook);
    }

    /**
     * {@code DELETE  /userbooks/:id} : delete the "id" userBook.
     *
     * @param id the id of the userBook to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/userbooks/{id}")
    public ResponseEntity<Void> deleteUserBook(@PathVariable Long id) {
        log.debug("REST request to delete UserBook : {}", id);
        userBookService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/userbooks/user/{id}")
    public ResponseEntity<List<UserBook>> findAllUserbooksByUserId(@PathVariable Long id, Pageable pageable){
        log.debug("REST request to get Userbook by user id: {id}", id);
        Page<UserBook> userBooks = userBookService.findAllUserbooksByUserId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), userBooks);
        return ResponseEntity.ok().headers(headers).body(userBooks.getContent());
    }


}
