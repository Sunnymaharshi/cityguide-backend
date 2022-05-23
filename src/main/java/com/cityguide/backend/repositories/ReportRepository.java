package com.cityguide.backend.repositories;

import com.cityguide.backend.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Integer> {
}
