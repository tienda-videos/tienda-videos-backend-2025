package com.example.tienda_videos.services;

import org.springframework.data.domain.Page;

import com.example.tienda_videos.models.dtos.CountryDto;

public interface CountryService {
    Page<CountryDto> getAllCountries(String name, Integer page, Integer size);
    CountryDto getCountryById(String id);
    CountryDto createCountry(CountryDto countryDto);
    CountryDto updateCountry(String id, CountryDto countryDto);
    void deleteCountry(String id);
}