package com.sue.cars.repository;

import com.sue.cars.dtos.CarDTO;
import com.sue.cars.dtos.diplay.DisplayCarDTO;
import com.sue.cars.entity.Car;
import com.sue.cars.entity.ModelBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface carRepository extends JpaRepository<Car, Long> {
    public List<Car> findByVin(String vin);
    DisplayCarDTO findByDolVehicleId(Long dolVehicleId);

    Optional<Car> findCarByModelBrand(ModelBrand ModelBrand);
}
