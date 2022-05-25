package com.cityguide.backend.repositories;

import com.cityguide.backend.entities.Images;
import com.cityguide.backend.entities.Report;
import com.cityguide.backend.entities.Upvote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Integer> {

    @Query("select r from Report r where r.report_type = :#{#type}")
    List<Report> findReports(@Param("type") String type);

    @Query("select r from Report r where r.report_type = :#{#rtype} and r.report_type_id = :#{#rtypeid}")
    Report findReport(@Param("rtype") String type, @Param("rtypeid") int typeid);
}
