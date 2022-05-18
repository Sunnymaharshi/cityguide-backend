package com.cityguide.backend.repositories;

import com.cityguide.backend.entities.Downvote;
import com.cityguide.backend.entities.Upvote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DownvoteRepository extends JpaRepository<Downvote,Integer> {

    @Query("select d from Downvote d where d.username = :#{#username} and d.ans_id = :#{#ansid}")
    Optional<Downvote> findUserDownvote(@Param("username") String user, @Param("ansid") int ansid);

    @Query("select d from Downvote d where d.username = :#{#username} and d.ans_id = :#{#ansid}")
    Downvote findDownvote(@Param("username") String user, @Param("ansid") int ansid);
}
