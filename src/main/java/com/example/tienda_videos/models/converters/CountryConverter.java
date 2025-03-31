package com.example.tienda_videos.models.converters;

import com.example.tienda_videos.models.dtos.CountryDto;
import com.example.tienda_videos.models.entities.CountryEntity;
import com.example.tienda_videos.services.CryptoService;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CountryConverter {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    
    @Autowired
    private CryptoService cryptoService;

    public CountryDto entityToDto(CountryEntity entity) {
        if (entity == null) return null;

        return CountryDto.builder()
                .countryId(encryptId(entity.getCountryId()))
                .country(entity.getCountry())
                .lastUpdate(entity.getLastUpdate())
                .createdAt(entity.getCreatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    public CountryEntity dtoToEntity(CountryDto dto) {
        if (dto == null) return null;

        validateDto(dto); // Throws exception if invalid

        return CountryEntity.builder()
                .countryId(decryptId(dto.getCountryId()))
                .country(dto.getCountry())
                .lastUpdate(dto.getLastUpdate())
                .createdAt(dto.getCreatedAt())
                .deletedAt(dto.getDeletedAt())
                .build();
    }

    private String encryptId(Short id) {
        return id != null ? cryptoService.encrypt(id.toString()) : null;
    }

    private Short decryptId(String encryptedId) {
        return encryptedId != null ? Short.parseShort(cryptoService.decrypt(encryptedId)) : null;
    }

    // Validation
    private void validateDto(CountryDto dto) {
        Set<String> violations = validator.validate(dto)
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.toSet());

        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Validation errors:\n" + String.join("\n", violations));
        }
    }
}