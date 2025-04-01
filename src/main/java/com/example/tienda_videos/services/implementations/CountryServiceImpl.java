package com.example.tienda_videos.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tienda_videos.models.converters.CountryConverter;
import com.example.tienda_videos.models.dtos.CountryDto;
import com.example.tienda_videos.models.entities.CountryEntity;
import com.example.tienda_videos.models.specifications.CountrySpecification;
import com.example.tienda_videos.repositories.CountryRepository;
import com.example.tienda_videos.services.CountryService;
import com.example.tienda_videos.services.CryptoService;
import com.example.tienda_videos.utils.exceptions.custom_exceptions.NotFoundException;


@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CountryConverter countryConverter;

    @Autowired
    private CryptoService cryptoService;

    @Transactional(readOnly = true)
    @Cacheable(value = "countries", key = "#root.methodName + '_' + T(java.util.Objects).toString(#name, 'null') + '_' + T(java.util.Objects).toString(#page, 'null') + '_' + T(java.util.Objects).toString(#size, 'null')")
    @Override
    public Page<CountryDto> getAllCountries(String name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        if (page != null && size != null) {
            pageable = PageRequest.of(page, size);
        }
        
        Specification<CountryEntity> spec = Specification.where(CountrySpecification.filterByName(name));
        Page<CountryEntity> countryPage = countryRepository.findAll(spec, pageable);
        return countryPage.map(countryConverter::entityToDto);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "country", key = "#id")
    @Override
    public CountryDto getCountryById(String id) {
        Integer idDesencriptado = desencriptarIdProduct(id);
        CountryEntity country = getCountryEntity(idDesencriptado);
        return countryConverter.entityToDto(country);
    }

    @Transactional
    @CachePut(value = "country", key = "#result.countryId")
    @Override
    public CountryDto createCountry(CountryDto countryDto) {
        CountryEntity country = countryConverter.dtoToEntity(countryDto);
        CountryEntity savedCountry = countryRepository.save(country);
        return countryConverter.entityToDto(savedCountry);
    }

    @Transactional
    @CachePut(value = "country", key = "#result.countryId")
    @Override
    public CountryDto updateCountry(String id, CountryDto countryDto) {
        Integer idDesencriptado = desencriptarIdProduct(id);
        CountryEntity existingCountry = getCountryEntity(idDesencriptado);
        existingCountry.setCountry(countryDto.getCountry());
        CountryEntity updatedCountry = countryRepository.save(existingCountry);
        return countryConverter.entityToDto(updatedCountry);
    }

    @Transactional
    @CacheEvict(value = "country", key = "#id")
    @Override
    public void deleteCountry(String id) {
        Integer idDesencriptado = desencriptarIdProduct(id);
        CountryEntity country = getCountryEntity(idDesencriptado);
        country.markAsDeleted();
        countryRepository.save(country);
    }
    public int desencriptarIdProduct(String idEncriptado) {
        return Integer.parseInt(cryptoService.decrypt(idEncriptado));
    }


    private CountryEntity getCountryEntity(Integer id) {

        return countryRepository.findByCountryIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFoundException("Country not found with ID: " + id));
    }
}