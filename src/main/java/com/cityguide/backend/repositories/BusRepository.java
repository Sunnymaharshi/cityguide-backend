package com.cityguide.backend.repositories;


import com.cityguide.backend.entities.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, Integer> {
}
