package com.sue.cars.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sue.cars.dtos.CountryDTO;
import com.sue.cars.entity.Country;
import com.sue.cars.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/country")
public class countryController implements CRUDController2{
    @Autowired
    private CountryService service;

    @Override
    public List<CountryDTO> getAll() {
        return service.getAll();
    }

    @Override
    public CountryDTO getById(Long id) {
        return (CountryDTO) service.getById(id);
    }

    @Override
    public CountryDTO getByName(String name) {
        return service.getCountry(name);
    }

    @Override
    public CountryDTO add(Object countryDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        CountryDTO country = objectMapper.convertValue(countryDTO, CountryDTO.class);
        return (CountryDTO) service.addEntity(country);
    }

    @Override
    public CountryDTO update(Object countryDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        CountryDTO country = objectMapper.convertValue(countryDTO, CountryDTO.class);
        return (CountryDTO)service.updateEntity(country);
    }
    @Override
    public void deleteById(Long id) {
        service.deleteEntity(id);
    }


}
