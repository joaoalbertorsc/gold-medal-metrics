package com.codecademy.goldmedal.repositories;

import com.codecademy.goldmedal.model.GoldMedal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoldMedalRepository extends JpaRepository<GoldMedal, Integer> {
    int countByCountryAndGender(String countryName, String women);
    int countBySeason(String winter);
    int countByCountry(String countryName);
    List<GoldMedal> getByCountryAndSeasonOrderByYearAsc(String countryName, String summer);
    List<GoldMedal> getByCountryOrderByYearAsc(String countryName);
    List<GoldMedal> getByCountryOrderByYearDesc(String countryName);
    List<GoldMedal> getByCountryOrderBySeasonAsc(String countryName);
    List<GoldMedal> getByCountryOrderBySeasonDesc(String countryName);
    List<GoldMedal> getByCountryOrderByCityAsc(String countryName);
    List<GoldMedal> getByCountryOrderByCityDesc(String countryName);
    List<GoldMedal> getByCountryOrderByNameAsc(String countryName);
    List<GoldMedal> getByCountryOrderByNameDesc(String countryName);
    List<GoldMedal> getByCountryOrderByEventAsc(String countryName);
    List<GoldMedal> getByCountryOrderByEventDesc(String countryName);
}
