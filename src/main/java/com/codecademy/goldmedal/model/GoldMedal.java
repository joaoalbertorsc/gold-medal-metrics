package com.codecademy.goldmedal.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GoldMedal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer year;
    private String city;
    private String sport;
    private String discipline;
    private String name;
    private String country;
    private String gender;
    private String event;
    private String season;

    public GoldMedal() {}

    public GoldMedal(Integer year, String city, String sport, String discipline, String name, String country, String gender, String event, String season) {
        this.year = year;
        this.city = city;
        this.sport = sport;
        this.discipline = discipline;
        this.name = name;
        this.country = country;
        this.gender = gender;
        this.event = event;
        this.season = season;
    }

    public Long getId() {
        return id;
    }

    public Integer getYear() {
        return year;
    }

    public String getCity() {
        return city;
    }

    public String getSport() {
        return sport;
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getGender() {
        return gender;
    }

    public String getEvent() {
        return event;
    }

    public String getSeason() {
        return season;
    }
}