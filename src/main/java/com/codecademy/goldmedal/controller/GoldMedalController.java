package com.codecademy.goldmedal.controller;

import com.codecademy.goldmedal.model.*;
import com.codecademy.goldmedal.repositories.CountryRepository;
import com.codecademy.goldmedal.repositories.GoldMedalRepository;
import org.apache.commons.text.WordUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/countries")
public class GoldMedalController {
    // TODO: declare references to your repositories
    private final GoldMedalRepository goldMedalRepository;
    private final CountryRepository countryRepository;
    // TODO: update your constructor to include your repositories
    public GoldMedalController(GoldMedalRepository goldMedalRepository,
                               CountryRepository countryRepository) {
        this.goldMedalRepository = goldMedalRepository;
        this.countryRepository = countryRepository;
    }

    @GetMapping
    public CountriesResponse getCountries(@RequestParam String sort_by, @RequestParam String ascending) throws Exception {
        var ascendingOrder = ascending.toLowerCase().equals("y");
        return new CountriesResponse(getCountrySummaries(sort_by.toLowerCase(), ascendingOrder));
    }

    @GetMapping("/{country}")
    public CountryDetailsResponse getCountryDetails(@PathVariable String country) throws Exception {
        return getCountryDetailsResponse(country);
    }

    @GetMapping("/{country}/medals")
    public CountryMedalsListResponse getCountryMedalsList(@PathVariable String country, @RequestParam String sort_by, @RequestParam String ascending) {
        String countryName = WordUtils.capitalizeFully(country);
        var ascendingOrder = ascending.equalsIgnoreCase("y");
        return getCountryMedalsListResponse(countryName, sort_by.toLowerCase(), ascendingOrder);
    }

    private CountryMedalsListResponse getCountryMedalsListResponse(String countryName, String sortBy, boolean ascendingOrder) {
        List<GoldMedal> medalsList;
        switch (sortBy) {
            case "year":
                // TODO: list of medals sorted by year in the given order
                medalsList = ascendingOrder ?
                        goldMedalRepository.getByCountryOrderByYearAsc(countryName) :
                        goldMedalRepository.getByCountryOrderByYearDesc(countryName);
                break;
            case "season":
                // TODO: list of medals sorted by season in the given order
                medalsList = ascendingOrder ?
                        goldMedalRepository.getByCountryOrderBySeasonAsc(countryName) :
                        goldMedalRepository.getByCountryOrderBySeasonDesc(countryName);
                break;
            case "city":
                // TODO: list of medals sorted by city in the given order
                medalsList = ascendingOrder ?
                        goldMedalRepository.getByCountryOrderByCityAsc(countryName) :
                        goldMedalRepository.getByCountryOrderByCityDesc(countryName);
                break;
            case "name":
                // TODO: list of medals sorted by athlete's name in the given order
                medalsList = ascendingOrder ?
                        goldMedalRepository.getByCountryOrderByNameAsc(countryName) :
                        goldMedalRepository.getByCountryOrderByNameDesc(countryName);
                break;
            case "event":
                // TODO: list of medals sorted by event in the given order
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

    private CountryDetailsResponse getCountryDetailsResponse(String countryName) {
        // TODO: get the country; this repository method should return a java.util.Optional
        Optional<Country> countryOptional = this.countryRepository.findByName(countryName);
        if (countryOptional.isEmpty()) {
            return new CountryDetailsResponse(countryName);
        }

        var country = countryOptional.get();
        var goldMedalCount = goldMedalRepository.countByCountry(countryName);// TODO: get the medal count

        // TODO: get the collection of wins at the Summer Olympics, sorted by year in ascending order
        var summerWins = goldMedalRepository.getByCountryAndSeasonOrderByYearAsc(countryName, "Summer");
        var numberSummerWins = !summerWins.isEmpty() ? summerWins.size() : null;
        // TODO: get the total number of events at the Summer Olympics
        var totalSummerEvents = goldMedalRepository.countBySeason("Summer");
        var percentageTotalSummerWins = totalSummerEvents != 0 && numberSummerWins != null ? (float) summerWins.size() / totalSummerEvents : null;
        var yearFirstSummerWin = !summerWins.isEmpty() ? summerWins.get(0).getYear() : null;

        // TODO: get the collection of wins at the Winter Olympics, sorted by year in ascending order
        var winterWins = goldMedalRepository.getByCountryAndSeasonOrderByYearAsc(countryName, "Winter");
        var numberWinterWins = !winterWins.isEmpty() ? winterWins.size() : null;
        // TODO: get the total number of events at the Winter Olympics
        var totalWinterEvents = goldMedalRepository.countBySeason("Winter");
        var percentageTotalWinterWins = totalWinterEvents != 0 && numberWinterWins != null ? (float) winterWins.size() / totalWinterEvents : null;
        var yearFirstWinterWin = !winterWins.isEmpty() ? winterWins.get(0).getYear() : null;

        // TODO: get the number of wins by female athletes
        var numberEventsWonByFemaleAthletes = goldMedalRepository.countByCountryAndGender(countryName, "Women");
        // TODO: get the number of wins by male athletes
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

    private List<CountrySummary> getCountrySummaries(String sortBy, boolean ascendingOrder) throws Exception {
        List<Country> countries;
        switch (sortBy) {
            case "name":
                // TODO: list of countries sorted by name in the given order
                countries = ascendingOrder ?
                        countryRepository.findAllByOrderByNameAsc() :
                        countryRepository.findAllByOrderByNameDesc();
                break;
            case "gdp":
                // TODO: list of countries sorted by gdp in the given order
                countries = ascendingOrder ?
                        countryRepository.findAllByOrderByGdpAsc() :
                        countryRepository.findAllByOrderByGdpDesc();
                break;
            case "population":
                // TODO: list of countries sorted by population in the given order
                countries = ascendingOrder ?
                        countryRepository.findAllByOrderByPopulationAsc() :
                        countryRepository.findAllByOrderByPopulationDesc();
                break;
            case "medals":
            default:
                // TODO: list of countries in any order you choose; for sorting by medal count, additional logic below will handle that
                countries = countryRepository.findAllByOrderByNameAsc();
                break;
        }

        var countrySummaries = getCountrySummariesWithMedalCount(countries);

        if (sortBy.equalsIgnoreCase("medals")) {
            countrySummaries = sortByMedalCount(countrySummaries, ascendingOrder);
        }

        return countrySummaries;
    }

    private List<CountrySummary> sortByMedalCount(List<CountrySummary> countrySummaries, boolean ascendingOrder) {
        return countrySummaries.stream()
                .sorted((t1, t2) -> ascendingOrder ?
                        t1.getMedals() - t2.getMedals() :
                        t2.getMedals() - t1.getMedals())
                .collect(Collectors.toList());
    }
    private List<CountrySummary> getCountrySummariesWithMedalCount(List<Country> countries) throws Exception {
        List<CountrySummary> countrySummaries = new ArrayList<>();
        for (var country : countries) {
            // TODO: get count of medals for the given country
            var goldMedalCount = Integer.parseInt(getCountryDetails(country.getName()).getNumberMedals());
            countrySummaries.add(new CountrySummary(country, goldMedalCount));
        }
        return countrySummaries;
    }
}
