package com.elloquito.baseloquito.repository;

import com.elloquito.baseloquito.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Integer> {
}
