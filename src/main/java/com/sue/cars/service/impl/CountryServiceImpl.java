package com.sue.cars.service.impl;

import com.sue.cars.dtos.CityDTO;
import com.sue.cars.dtos.CountryDTO;
import com.sue.cars.entity.City;
import com.sue.cars.entity.Country;
import com.sue.cars.mappers.CityMapper;
import com.sue.cars.mappers.CountryMapper;
import com.sue.cars.repository.countryRepository;
import com.sue.cars.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private countryRepository countryRep;
    @Autowired
    private CountryMapper countryMapper;

    @Override
    public List<CountryDTO> getAll() {
        return countryRep.findAll().stream().map(country -> countryMapper.countryToCountryDTO(country)).collect(Collectors.toList());
    }

    @Override
    public CountryDTO getCountry(String name) {
        Optional<Country> country = countryRep.findByName(name);
        if(country.isPresent())
            return countryMapper.countryToCountryDTO(country.get());
        return null;
    }
    @Override
    public CountryDTO getById(Long id) {
        Optional<Country> country = countryRep.findById(id);
        if(country.isPresent())
            return countryMapper.countryToCountryDTO(country.get());
        return null;
    }

    @Override
    public CountryDTO addEntity(Object countryObject) {
        CountryDTO countryDTO = (CountryDTO) countryObject;
        countryRep.save(countryMapper.countryDtoToCountry(countryDTO));
        return countryDTO;
    }

    @Override
    public CountryDTO updateEntity(Object countryObject) {
        CountryDTO countryDTO = (CountryDTO) countryObject;
        if(countryRep.findById(countryDTO.getId()).isPresent()){
            countryRep.save(countryMapper.countryDtoToCountry(countryDTO));
            return countryDTO;
        }
        return null;
    }

    @Override
    public void deleteEntity(Long id) {
        Optional<Country> country = countryRep.findById(id);
        if(country.isPresent())
            countryRep.delete(country.get());
        else
            System.out.println("This Country Is Not Exist!");
    }
}
