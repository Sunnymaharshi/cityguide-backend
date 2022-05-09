package com.cityguide.backend.repositories;

import com.cityguide.backend.entities.Attractions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionsRepository extends JpaRepository<Attractions,Integer> {
}
