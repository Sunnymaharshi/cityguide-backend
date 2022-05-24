package com.cityguide.backend.repositories;

import com.cityguide.backend.entities.Images;
import com.cityguide.backend.entities.Upvote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Images,Integer> {

    @Query("select i from Images i where i.type = :#{#type} and i.type_id = :#{#type_id}")
    List<Images> findimages(@Param("type") String type, @Param("type_id") String typeid);
}
