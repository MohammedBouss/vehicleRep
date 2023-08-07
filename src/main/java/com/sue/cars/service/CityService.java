package com.sue.cars.service;

import com.sue.cars.dtos.CityDTO;
import com.sue.cars.dtos.diplay.DisplayCityDTO;

public interface CityService extends ServiceCRUD{
    DisplayCityDTO getCity(String name);
}
