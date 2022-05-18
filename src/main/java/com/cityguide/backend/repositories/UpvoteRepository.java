package com.cityguide.backend.repositories;

import com.cityguide.backend.entities.Upvote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UpvoteRepository extends JpaRepository<Upvote,Integer> {

    @Query("select u from Upvote u where u.username = :#{#username} and u.ans_id = :#{#ansid}")
    Optional<Upvote> findUserUpvote(@Param("username") String user,@Param("ansid") int ansid);

    @Query("select u from Upvote u where u.username = :#{#username} and u.ans_id = :#{#ansid}")
    Upvote findUpvote(@Param("username") String user,@Param("ansid") int ansid);
}
