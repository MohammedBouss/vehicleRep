package com.sue.cars.service;

import com.sue.cars.dtos.StateDTO;
import com.sue.cars.dtos.diplay.DisplayStateDTO;
import com.sue.cars.entity.State;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StateService extends ServiceCRUD{
    DisplayStateDTO getState(String name);
    List<State> findByCountry(Long id);
    DisplayStateDTO findByName(String name);
    List<String> findStateByCountryName(String name);
}
