package com.ibdbcompany.ibdb.service;

import com.ibdbcompany.ibdb.domain.Book;
import com.ibdbcompany.ibdb.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Book}.
 */
@Service
@Transactional
public class BookService {

    private final Logger log = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     *
     *
     * @param book the entity to save.
     * @return the persisted entity.
     */
    public Book save(Book book) {
        log.debug("Request to save Book : {}", book);
        return bookRepository.save(book);
    }

    /**
     * Get all the books.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Book> findAll(Pageable pageable) {
        log.debug("Request to get all Books");
        return bookRepository.findAll(pageable);
    }


    /**
     * Get all the books with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Book> findAllWithEagerRelationships(Pageable pageable) {
        return bookRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one book by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Book> findOne(Long id) {
        log.debug("Request to get Book : {}", id);
        return bookRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the book by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Book : {}", id);
        bookRepository.deleteById(id);
    }
    /**
     * Get books by title
     *
     * @param title
     * @param pageable
     * @return
     */
    public Page<Book> findAllBookByTitle(String title, Pageable pageable){
        log.debug("Request to get Book by title : {}", title);
        return bookRepository.findAllByTitleContaining(title, pageable);
    }

    /**
     * Get books by author
     *
     * @param firstName
     * @param pageable
     * @return
     */
    public Page<Book> findAllBookByAuthor(String firstName, Pageable pageable){
        log.debug("Request to get Book by author : {}", firstName);
        return bookRepository.findBooksByAuthor_FirstNameContaining(firstName, pageable);
    }

    /**
     *  Get Book by quote
     * @param id
     * @return
     */
    public Optional<Book> findBookByQuote (Long id){
        log.debug("Request to get Book by quote : {}", id);
        return bookRepository.findBookByQuote(id);
    }
}
