package com.ibdbcompany.ibdb.repository;

import com.ibdbcompany.ibdb.domain.Role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Role entity.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "select distinct role from Role role  left join fetch role.actions",
        countQuery = "select count(distinct role) from Role role")
    Page<Role> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct role from Role role  left join fetch role.actions")
    List<Role> findAllWithEagerRelationships();

    @Query("select role from Role role left join fetch role.actions where role.id =:id")
    Optional<Role> findOneWithEagerRelationships(@Param("id") Long id);

    Optional<Role> findOneByName(String name);
}
