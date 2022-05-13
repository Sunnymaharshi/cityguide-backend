package com.cityguide.backend.repositories;

import com.cityguide.backend.entities.Downvote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DownvoteRepository extends JpaRepository<Downvote,Integer> {
}
