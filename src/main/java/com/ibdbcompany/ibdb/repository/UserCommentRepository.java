package com.ibdbcompany.ibdb.repository;


import com.ibdbcompany.ibdb.domain.UserComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserCommentRepository extends JpaRepository<UserComment, Long> {

    //@Query("select usercomment from UserComment usercomment left join fetch usercomment where usercomment.id=:id")
    Page<UserComment> findUserCommentsByUserId(Long id, Pageable pageable);
}
