package com.ibdbcompany.ibdb.repository;

import com.ibdbcompany.ibdb.domain.Book;
import com.ibdbcompany.ibdb.domain.User;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.time.Instant;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);

    Page<User> findAllByLoginNotAndLoginContains(String hidden, String login, Pageable pageable);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByLogin(String login);

    @EntityGraph(attributePaths = {"roles.actions", "imageModel"})
   // @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Optional<User> findOneWithRolesByLogin(String login);

    @EntityGraph(attributePaths = {"roles.actions", "imageModel"})
  //  @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Optional<User> findOneWithRolesByEmailIgnoreCase(String email);

    Page<User> findAllByLoginNot(Pageable pageable, String login);

/*
    @Query(value = "select distinct user from User user left join fetch user.roles",
        countQuery = "select count(distinct user) from User user")
    Page<User> findAllWithEagerRelationships(Pageable pageable);
    @Query("select distinct user from User user left join fetch user.roles")
    List<Book> findAllWithEagerRelationships();


    @Query("select user from User user left join fetch user.roles where user.login =:login")
    Optional<User> findOneWithEagerRelationships(@Param("login") String login);
*/
    @Modifying
    @Query("delete from User user where user.login=:login")
    void deteteUserByLogin(@Param("login") String login);

}
