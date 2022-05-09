package com.cityguide.backend.repositories;

import com.cityguide.backend.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,String> {
}
