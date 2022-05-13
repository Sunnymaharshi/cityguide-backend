package com.cityguide.backend.repositories;

import com.cityguide.backend.entities.Upvote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpvoteRepository extends JpaRepository<Upvote,Integer> {
}
