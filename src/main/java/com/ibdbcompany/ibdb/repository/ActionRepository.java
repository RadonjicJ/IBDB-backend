package com.ibdbcompany.ibdb.repository;

import com.ibdbcompany.ibdb.domain.Action;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Action entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
    Page<Action> findAllByNameContains(String name, Pageable pageable);
}
