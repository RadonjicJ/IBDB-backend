package com.ibdbcompany.ibdb.service;

import com.ibdbcompany.ibdb.domain.Comment;
import com.ibdbcompany.ibdb.domain.UserBook;
import com.ibdbcompany.ibdb.repository.UserBookRepository;
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
public class UserBookService {

    private final Logger log = LoggerFactory.getLogger(UserBookService.class);

    private final UserBookRepository userBookRepository;

    public UserBookService(UserBookRepository userBookRepository){
        this.userBookRepository = userBookRepository;
    }

    /**
     * Save a userBook.
     *
     * @param userBook the entity to save.
     * @return the persisted entity.
     */
    public UserBook save(UserBook userBook) {
        log.debug("Request to save UserBook : {}", userBook);
        return userBookRepository.save(userBook);
    }

    /**
     * Get all the userBook.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserBook> findAll(Pageable pageable) {
        log.debug("Request to get all UserBook");
        return userBookRepository.findAll(pageable);
    }

    /**
     * Get one userBook by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserBook> findOne(Long id) {
        log.debug("Request to get UserBook : {}", id);
        return userBookRepository.findById(id);
    }

    /**
     * Delete the userBook by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserBook : {}", id);
        userBookRepository.deleteById(id);
    }

    /**
     *
     * @param id
     * @param pageable
     * @return
     */
    public Page<UserBook> findAllUserbooksByUserId(Long id, Pageable pageable) {
        log.debug("Request to get Usebooks by user id: {}", id);
        return userBookRepository.findUserBooksByUserId(id, pageable);
    }

}
