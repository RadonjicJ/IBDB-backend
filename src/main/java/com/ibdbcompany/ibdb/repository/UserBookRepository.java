package com.ibdbcompany.ibdb.repository;

import com.ibdbcompany.ibdb.domain.UserBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {

    //@Query("select userbook from UserBook userbook left join fetch userbook where userbook.id=:id")
    Page<UserBook> findUserBooksByUserId(Long id, Pageable pageable);
}
