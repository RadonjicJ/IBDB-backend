package com.ibdbcompany.ibdb.repository;

import com.ibdbcompany.ibdb.domain.Book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Book entity.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "select distinct book from Book book left join fetch book.categories",
        countQuery = "select count(distinct book) from Book book")
    Page<Book> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct book from Book book left join fetch book.categories")
    List<Book> findAllWithEagerRelationships();

    @Query("select book from Book book left join fetch book.categories where book.id =:id")
    Optional<Book> findOneWithEagerRelationships(@Param("id") Long id);

    Page<Book> findBooksByAuthorId (Long id, Pageable pageable);
}
