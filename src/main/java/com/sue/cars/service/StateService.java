package com.sue.cars.service;

import com.sue.cars.dtos.StateDTO;
import com.sue.cars.dtos.diplay.DisplayStateDTO;
import com.sue.cars.entity.State;

import java.util.List;
import java.util.Optional;

public interface StateService extends ServiceCRUD{
    DisplayStateDTO getState(String name);
}
