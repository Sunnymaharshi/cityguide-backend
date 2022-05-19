package com.cityguide.backend.repositories;

import com.cityguide.backend.entities.MetroMap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetroMapRepository extends JpaRepository<MetroMap,Integer> {
}
