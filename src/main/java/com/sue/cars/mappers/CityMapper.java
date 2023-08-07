package com.sue.cars.mappers;

import com.sue.cars.dtos.CityDTO;
import com.sue.cars.dtos.StateDTO;
import com.sue.cars.dtos.diplay.DisplayCityDTO;
import com.sue.cars.entity.City;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private StateMapper stateMapper;

    public DisplayCityDTO cityToCityDTO(City city){
        DisplayCityDTO displayCityDTO = modelMapper.map(city, DisplayCityDTO.class);
        displayCityDTO.setStateName(city.getState().getName());
        displayCityDTO.setCountryName(city.getState().getCountry().getName());
         return displayCityDTO;
    }

    public City cityDtoToCity(CityDTO cityDTO){
        City city = modelMapper.map(cityDTO,City.class);
        return city;
    }

}
