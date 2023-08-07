package com.sue.cars.service.impl;

import com.sue.cars.dtos.StateDTO;
import com.sue.cars.dtos.diplay.DisplayStateDTO;
import com.sue.cars.entity.Country;
import com.sue.cars.entity.State;
import com.sue.cars.mappers.StateMapper;
import com.sue.cars.repository.countryRepository;
import com.sue.cars.repository.stateRepository;
import com.sue.cars.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StateServiceImpl implements StateService {

    private final stateRepository stateRep;
    private final countryRepository countryRep;
    private final StateMapper stateMapper;
    public StateServiceImpl(stateRepository stateRep, countryRepository countryRep, StateMapper stateMapper) {
        this.stateRep = stateRep;
        this.countryRep = countryRep;
        this.stateMapper = stateMapper;
    }

    @Override
    public List<DisplayStateDTO> getAll() {
        return stateRep.findAll().stream().map(state -> stateMapper.stateToStateDTO(state))
                .collect(Collectors.toList());
    }

    @Override
    public DisplayStateDTO getById(Long id) {
        Optional<State> state = stateRep.findById(id);
        if(state.isPresent()){
            return stateMapper.stateToStateDTO(state.get());
        }
        return null;
    }

    @Override
    public DisplayStateDTO getState(String name) {
         Optional<State> state = stateRep.findByName(name);
         if(state.isPresent()){
             return stateMapper.stateToStateDTO(state.get());
         }
         return null;
    }

    @Override
    public DisplayStateDTO addEntity(Object stateObject) {
        StateDTO stateDTO = (StateDTO) stateObject;
        Optional<Country> country = countryRep.findById(stateDTO.getCountryId());
        if(country.isPresent()){
            State state = stateMapper.stateDtoToState(stateDTO);
            state.setCountry(country.get());
            return stateMapper.stateToStateDTO(stateRep.save(state));
        }
        return null;
    }

    @Override
    public DisplayStateDTO updateEntity(Object stateObject) {
        StateDTO stateDTO = (StateDTO) stateObject;
        if(stateRep.findById(stateDTO.getId()).isPresent()){
            Optional<Country> country = countryRep.findById(stateDTO.getCountryId());
            State state = stateMapper.stateDtoToState(stateDTO);
            state.setCountry(country.get());
            return stateMapper.stateToStateDTO(stateRep.save(state));
        }
        return null;
    }

    @Override
    public void deleteEntity(Long id) {
        Optional<State> state = stateRep.findById(id);
        if(state.isPresent())
            stateRep.delete(state.get());
        else
            System.out.println("this state is not exist!");
    }
}
