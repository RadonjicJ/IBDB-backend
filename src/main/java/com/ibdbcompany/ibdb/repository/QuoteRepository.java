package com.ibdbcompany.ibdb.repository;

import com.ibdbcompany.ibdb.domain.Quote;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Quote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    Page<Quote> findQuotesByBookId (Long id, Pageable pageable);

    Page<Quote> findQuotesByAuthorId (Long id, Pageable pageable);
}

