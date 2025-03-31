package com.example.tienda_videos.models.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.example.tienda_videos.models.entities.CountryEntity;

public class CountrySpecification {
    
    public static Specification<CountryEntity> filterByName(String name) {
        return (root, query, cb) -> {
            if (name == null) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("country")), "%" + name.toLowerCase() + "%");
        };
    }
}