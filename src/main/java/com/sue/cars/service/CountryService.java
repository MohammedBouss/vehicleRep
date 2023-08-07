package com.sue.cars.service;

import com.sue.cars.dtos.CountryDTO;

public interface CountryService extends ServiceCRUD{
    public CountryDTO getCountry(String name);
}
