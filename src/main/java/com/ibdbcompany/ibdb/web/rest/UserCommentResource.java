package com.ibdbcompany.ibdb.web.rest;

import com.ibdbcompany.ibdb.domain.UserComment;
import com.ibdbcompany.ibdb.service.UserCommentService;
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
public class UserCommentResource {

    private final Logger log = LoggerFactory.getLogger(UserCommentResource.class);

    private final static String ENTITY_NAME = "user_comment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserCommentService userCommentService;

    public UserCommentResource(UserCommentService userCommentService) {
        this.userCommentService = userCommentService;
    }


    /**
     * {@code POST  /usercomments} : Create a new UserComment.
     *
     * @param userComment the userComment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userComment, or with status {@code 400 (Bad Request)} if the userComment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/usercomments")
    public ResponseEntity<UserComment> createUserComment(@Valid @RequestBody UserComment userComment) throws URISyntaxException {
        log.debug("REST request to save UserComment : {}", userComment);
        if (userComment.getId() != null && userComment.getId()!=0) {
            throw new BadRequestAlertException("A new userComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        userComment.setId(null);

        UserComment result = userCommentService.save(userComment);
        return ResponseEntity.created(new URI("/api/usercomments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /usercomments} : Updates an existing userComment.
     *
     * @param userComment the userComment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userComment,
     * or with status {@code 400 (Bad Request)} if the userComment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userCommetn couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/usercomments")
    public ResponseEntity<UserComment> updateUserComment(@Valid @RequestBody UserComment userComment) throws URISyntaxException {
        log.debug("REST request to update UserComment : {}", userComment);
        if (userComment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserComment result = userCommentService.save(userComment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userComment.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /usercomments} : get all the userComment.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userComment in body.
     */
    @GetMapping("/usercomments")
    public ResponseEntity<List<UserComment>> getAllUserComments(Pageable pageable) {
        log.debug("REST request to get a page of UserComment");
        Page<UserComment> page = userCommentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /usercomments/:id} : get the "id" userComment.
     *
     * @param id the id of the userComment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userComment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/usercomments/{id}")
    public ResponseEntity<UserComment> getUserComment(@PathVariable Long id) {
        log.debug("REST request to get UserComment : {}", id);
        Optional<UserComment> userComment = userCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userComment);
    }

    /**
     * {@code DELETE  /usercomments/:id} : delete the "id" userComment.
     *
     * @param id the id of the userComment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/usercomments/{id}")
    public ResponseEntity<Void> deleteUserComment(@PathVariable Long id) {
        log.debug("REST request to delete UserComment : {}", id);
        userCommentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/usercomments/user/{id}")
    public ResponseEntity<List<UserComment>> findAllUserCommentsByUserId(@PathVariable Long id, Pageable pageable){
        log.debug("REST request to get userComment by user id: {id}", id);
        Page<UserComment> userComments = userCommentService.findAllUserCommentsByUserId(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), userComments);
        return ResponseEntity.ok().headers(headers).body(userComments.getContent());
    }


}
