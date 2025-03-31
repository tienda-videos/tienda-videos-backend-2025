package com.example.tienda_videos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.tienda_videos.models.entities.CountryEntity;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Integer>, JpaSpecificationExecutor<CountryEntity> {
    Optional<CountryEntity> findByCountryIdAndDeletedAtIsNull(int countryId);
}