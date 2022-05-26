package com.cityguide.backend.repositories;

import com.cityguide.backend.entities.BookMarks;
import com.cityguide.backend.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookMarkRepository extends JpaRepository<BookMarks,Integer> {

    @Query("select b from BookMarks b where b.book_type = :#{#btype} and b.book_type_id = :#{#btypeid}")
    BookMarks findbookmark(@Param("btype") String type, @Param("btypeid") int typeid);
}
