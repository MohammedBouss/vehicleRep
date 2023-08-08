package com.sue.cars.repository;

import com.sue.cars.entity.Brand;
import com.sue.cars.entity.ModelBrand;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface modelBrandRepository extends JpaRepository<ModelBrand,Long> {
    Optional<ModelBrand> findByName(String name);
    List<ModelBrand> findModelBrandByBrandAndModelYear(Brand brand, int modelYaer);
    @Query("SELECT distinct (m.modelYear) FROM ModelBrand m")
    List<Integer> getDistinctModelYear();
}
