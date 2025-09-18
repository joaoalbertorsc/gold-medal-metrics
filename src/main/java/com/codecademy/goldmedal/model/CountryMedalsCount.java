package com.codecademy.goldmedal.model;

public class CountryMedalsCount {
    private final String name;
    private final Long count;

    public CountryMedalsCount(String name, Long count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public Long getCount() {
        return count;
    }
}
