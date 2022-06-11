package com.ibdbcompany.ibdb.service;

import com.ibdbcompany.ibdb.domain.Comment;
import com.ibdbcompany.ibdb.domain.UserComment;
import com.ibdbcompany.ibdb.repository.UserCommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserCommentService {

    private final Logger log = LoggerFactory.getLogger(UserCommentService.class);

    private final UserCommentRepository userCommentRepository;

    public UserCommentService(UserCommentRepository userCommentRepository){
        this.userCommentRepository = userCommentRepository;
    }

    /**
     * Save a userCommment.
     *
     * @param userComment the entity to save.
     * @return the persisted entity.
     */
    public UserComment save(UserComment userComment) {
        log.debug("Request to save UserComment : {}", userComment);
        return userCommentRepository.save(userComment);
    }

    /**
     * Get all the userComment.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserComment> findAll(Pageable pageable) {
        log.debug("Request to get all UserComment");
        return userCommentRepository.findAll(pageable);
    }

    /**
     * Get one userComment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserComment> findOne(Long id) {
        log.debug("Request to get UserComment : {}", id);
        return userCommentRepository.findById(id);
    }

    /**
     * Delete the userComment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserComment : {}", id);
        userCommentRepository.deleteById(id);
    }

    /**
     *
     * @param id
     * @param pageable
     * @return
     */
    public Page<UserComment> findAllUserCommentsByUserId(Long id, Pageable pageable) {
        log.debug("Request to get UserComment by user id: {}", id);
        return userCommentRepository.findUserCommentsByUserId(id, pageable);
    }

}
