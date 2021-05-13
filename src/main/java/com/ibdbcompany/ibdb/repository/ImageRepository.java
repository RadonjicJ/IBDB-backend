package com.ibdbcompany.ibdb.repository;

import java.util.Optional;

import com.ibdbcompany.ibdb.domain.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepository extends JpaRepository<ImageModel, Long> {
    Optional<ImageModel> findById(Long id);

}
