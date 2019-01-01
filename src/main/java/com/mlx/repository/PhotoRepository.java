package com.mlx.repository;

import com.mlx.domain.entities.Ad;
import com.mlx.domain.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer> {
}
