package com.sue.cars.repository;

import com.sue.cars.entity.ModelBrand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface modelBrandRepository extends JpaRepository<ModelBrand,Long> {
    Optional<ModelBrand> findByName(String name);
}
