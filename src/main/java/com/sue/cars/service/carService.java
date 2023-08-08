package com.sue.cars.service;

import com.sue.cars.dtos.CarDTO;
import com.sue.cars.dtos.ModelBrandDTO;
import com.sue.cars.dtos.diplay.DisplayCarDTO;
import com.sue.cars.dtos.diplay.DisplayModelBrand;
import com.sue.cars.entity.Car;
import com.sue.cars.entity.ModelBrand;
import org.springframework.data.domain.Page;

import java.util.List;

public interface carService extends ServiceCRUD{
    List<DisplayCarDTO> getCarByVin(String vin);
    void deleteCars(String vin);
    List<DisplayCarDTO> getAllCars(int offset, int pageSize);
    List<DisplayCarDTO> getCarByModelBrand(ModelBrandDTO modelBrand);
    Page<Car> getCarsByPage(int offset, int pageSize);
}
