package com.cityguide.backend.repositories;

import com.cityguide.backend.entities.Images;
import com.cityguide.backend.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Integer> {

    @Query("select r from Report r where r.report_type = :#{#type}")
    List<Report> findReport(@Param("type") String type);
}
