package com.codecademy.goldmedal.repositories;

import com.codecademy.goldmedal.model.GoldMedal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoldMedalRepository extends JpaRepository<GoldMedal, Integer> {

}
