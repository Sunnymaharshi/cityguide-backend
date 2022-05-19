package com.cityguide.backend.repositories;

import com.cityguide.backend.entities.BusMap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusMapRepository extends JpaRepository<BusMap,Integer> {
}
