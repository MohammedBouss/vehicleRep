package com.sue.cars.repository;

import com.sue.cars.entity.Car;
import com.sue.cars.entity.ModelBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface carRepository extends JpaRepository<Car, Long> {
    public List<Car> findByVin(String vin);
    Optional<Car> findByDolVehicleId(Long dolVehicleId);
    Optional<Car> findCarByModelBrand(ModelBrand ModelBrand);
}
