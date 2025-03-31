package com.example.tienda_videos.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryDto implements Serializable {
    private String countryId;

    @NotBlank(message = "El nombre del país no puede estar vacío.")
    @Size(max = 50, message = "El nombre del país no puede exceder los 50 caracteres.")
    private String country;

    private LocalDateTime lastUpdate;

    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
}