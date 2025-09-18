package com.codecademy.goldmedal.service;

import com.codecademy.goldmedal.model.*;
import com.codecademy.goldmedal.repositories.CountryRepository;
import com.codecademy.goldmedal.repositories.GoldMedalRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final GoldMedalRepository goldMedalRepository;
    private final CountryRepository countryRepository;

    public CountryService(GoldMedalRepository goldMedalRepository, CountryRepository countryRepository) {
        this.goldMedalRepository = goldMedalRepository;
        this.countryRepository = countryRepository;
    }

    @Cacheable("countries")
    public List<CountrySummary> getCountrySummaries(String sortBy, boolean ascendingOrder) {
        List<Country> countries;
        switch (sortBy) {
            case "name":
                countries = ascendingOrder ?
                        countryRepository.findAllByOrderByNameAsc() :
                        countryRepository.findAllByOrderByNameDesc();
                break;
            case "gdp":
                countries = ascendingOrder ?
                        countryRepository.findAllByOrderByGdpAsc() :
                        countryRepository.findAllByOrderByGdpDesc();
                break;
            case "population":
                countries = ascendingOrder ?
                        countryRepository.findAllByOrderByPopulationAsc() :
                        countryRepository.findAllByOrderByPopulationDesc();
                break;
            case "medals":
            default:
                countries = countryRepository.findAllByOrderByNameAsc();
                break;
        }

        var countrySummaries = getCountrySummariesWithMedalCount(countries);

        if (sortBy.equalsIgnoreCase("medals")) {
            countrySummaries = sortByMedalCount(countrySummaries, ascendingOrder);
        }

        return countrySummaries;
    }

    @Cacheable("countryDetails")
    public CountryDetailsResponse getCountryDetailsResponse(String countryName) {
        Optional<Country> countryOptional = this.countryRepository.findByName(countryName);
        if (countryOptional.isEmpty()) {
            return new CountryDetailsResponse(countryName);
        }

        var country = countryOptional.get();
        var goldMedalCount = goldMedalRepository.countByCountry(countryName);

        var summerWins = goldMedalRepository.getByCountryAndSeasonOrderByYearAsc(countryName, "Summer");
        var numberSummerWins = !summerWins.isEmpty() ? summerWins.size() : null;
        var totalSummerEvents = goldMedalRepository.countBySeason("Summer");
        var percentageTotalSummerWins = totalSummerEvents != 0 && numberSummerWins != null ? (float) summerWins.size() / totalSummerEvents : null;
        var yearFirstSummerWin = !summerWins.isEmpty() ? summerWins.get(0).getYear() : null;

        var winterWins = goldMedalRepository.getByCountryAndSeasonOrderByYearAsc(countryName, "Winter");
        var numberWinterWins = !winterWins.isEmpty() ? winterWins.size() : null;
        var totalWinterEvents = goldMedalRepository.countBySeason("Winter");
        var percentageTotalWinterWins = totalWinterEvents != 0 && numberWinterWins != null ? (float) winterWins.size() / totalWinterEvents : null;
        var yearFirstWinterWin = !winterWins.isEmpty() ? winterWins.get(0).getYear() : null;

        var numberEventsWonByFemaleAthletes = goldMedalRepository.countByCountryAndGender(countryName, "Women");
        var numberEventsWonByMaleAthletes = goldMedalRepository.countByCountryAndGender(countryName, "Men");

        return new CountryDetailsResponse(
                countryName,
                country.getGdp(),
                country.getPopulation(),
                goldMedalCount,
                numberSummerWins,
                percentageTotalSummerWins,
                yearFirstSummerWin,
                numberWinterWins,
                percentageTotalWinterWins,
                yearFirstWinterWin,
                numberEventsWonByFemaleAthletes,
                numberEventsWonByMaleAthletes);
    }

    @Cacheable("countryMedals")
    public CountryMedalsListResponse getCountryMedalsListResponse(String countryName, String sortBy, boolean ascendingOrder) {
        List<GoldMedal> medalsList;
        switch (sortBy) {
            case "year":
                medalsList = ascendingOrder ?
                        goldMedalRepository.getByCountryOrderByYearAsc(countryName) :
                        goldMedalRepository.getByCountryOrderByYearDesc(countryName);
                break;
            case "season":
                medalsList = ascendingOrder ?
                        goldMedalRepository.getByCountryOrderBySeasonAsc(countryName) :
                        goldMedalRepository.getByCountryOrderBySeasonDesc(countryName);
                break;
            case "city":
                medalsList = ascendingOrder ?
                        goldMedalRepository.getByCountryOrderByCityAsc(countryName) :
                        goldMedalRepository.getByCountryOrderByCityDesc(countryName);
                break;
            case "name":
                medalsList = ascendingOrder ?
                        goldMedalRepository.getByCountryOrderByNameAsc(countryName) :
                        goldMedalRepository.getByCountryOrderByNameDesc(countryName);
                break;
            case "event":
                medalsList = ascendingOrder ?
                        goldMedalRepository.getByCountryOrderByEventAsc(countryName) :
                        goldMedalRepository.getByCountryOrderByEventDesc(countryName);
                break;
            default:
                medalsList = new ArrayList<>();
                break;
        }

        return new CountryMedalsListResponse(medalsList);
    }

    private List<CountrySummary> sortByMedalCount(List<CountrySummary> countrySummaries, boolean ascendingOrder) {
        return countrySummaries.stream()
                .sorted((t1, t2) -> ascendingOrder ?
                        t1.getMedals() - t2.getMedals() :
                        t2.getMedals() - t1.getMedals())
                .collect(Collectors.toList());
    }

    private List<CountrySummary> getCountrySummariesWithMedalCount(List<Country> countries) {
        List<CountryMedalsCount> medalsCounts = goldMedalRepository.findTotalMedalsByCountry();
        Map<String, Long> medalsMap = medalsCounts.stream()
                .collect(Collectors.toMap(CountryMedalsCount::getName, CountryMedalsCount::getCount));

        List<CountrySummary> countrySummaries = new ArrayList<>();
        for (var country : countries) {
            var goldMedalCount = medalsMap.getOrDefault(country.getName(), 0L);
            countrySummaries.add(new CountrySummary(country, Math.toIntExact(goldMedalCount)));
        }
        return countrySummaries;
    }
}
