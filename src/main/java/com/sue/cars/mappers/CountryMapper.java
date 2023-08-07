package com.sue.cars.mappers;

import com.sue.cars.dtos.CountryDTO;
import com.sue.cars.entity.Country;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {
    @Autowired
    private ModelMapper modelMapper;
    public CountryDTO countryToCountryDTO(Country country){
        return modelMapper.map(country, CountryDTO.class);
    }
    public Country countryDtoToCountry(CountryDTO countryDTO){
        return modelMapper.map(countryDTO, Country.class);
    }
}
