package com.sue.cars.service;

import com.sue.cars.dtos.CarDTO;
import com.sue.cars.dtos.diplay.DisplayCarDTO;
import com.sue.cars.dtos.diplay.DisplayModelBrand;
import com.sue.cars.entity.ModelBrand;

import java.util.List;

public interface carService extends ServiceCRUD{
    List<DisplayCarDTO> getCarByVin(String vin);
    void deleteCars(String vin);
    List<DisplayCarDTO> getAllCars(int offset, int pageSize);
//    List<DisplayCarDTO> getCarByModelBrand(DisplayModelBrand modelBrand);
}
