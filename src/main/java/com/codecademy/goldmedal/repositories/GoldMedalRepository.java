package com.codecademy.goldmedal.repositories;

import com.codecademy.goldmedal.model.GoldMedal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GoldMedalRepository extends JpaRepository<GoldMedal, Integer> {

    List<GoldMedal> findBySeason(String summer);

    //@Query("SELECT DISTINCT g FROM gold_medal g WHERE g.season = :season GROUP BY g.event")
    List<GoldMedal> findDistinctBySeason(String season);

    //@Query("SELECT DISTINCT g FROM gold_medal g WHERE g.gender = :gender GROUP BY g.event")
    List<GoldMedal> findDistinctByGender(String gender);
}
