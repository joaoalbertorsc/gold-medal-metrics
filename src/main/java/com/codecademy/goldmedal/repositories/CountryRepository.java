package com.codecademy.goldmedal.repositories;

import com.codecademy.goldmedal.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    Optional<Country> findByName(String countryName);

    List<Country> findAllByOrderByNameAsc();

    List<Country> findAllByOrderByNameDesc();

    List<Country> findAllByOrderByGdpAsc();

    List<Country> findAllByOrderByGdpDesc();

    List<Country> findAllByOrderByPopulationAsc();

    List<Country> findAllByOrderByPopulationDesc();
}
