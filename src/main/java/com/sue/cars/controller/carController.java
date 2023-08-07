package com.sue.cars.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sue.cars.dtos.CarDTO;
import com.sue.cars.dtos.diplay.DisplayCarDTO;
import com.sue.cars.service.carService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/car")
public class carController implements CRUDController{

    @Autowired
    private carService carServ;

    @Override
    public List<DisplayCarDTO> getAll(){
        return carServ.getAll();
    }

    /*@GetMapping("/getCars/{offset}/{pageSize}")
    public List<DisplayCarDTO> getAllCars(@PathVariable int offset, @PathVariable int pageSize){
        List<DisplayCarDTO> list = carServ.getAllCars(offset, pageSize);
        list.stream().collect(Collectors.toList()).stream().forEach(displayCarDTO -> System.out.println(displayCarDTO));
        return list;
    }*/

    @Override
    public DisplayCarDTO getById(Long id){
        return (DisplayCarDTO) carServ.getById(id);
    }

    @Override
    public DisplayCarDTO add(Object carObject){
        ObjectMapper objectMapper = new ObjectMapper();
        CarDTO carDTO = objectMapper.convertValue(carObject, CarDTO.class);
        return (DisplayCarDTO) carServ.addEntity(carDTO);
    }

    @Override
    public DisplayCarDTO update(Object carObject){
        ObjectMapper objectMapper = new ObjectMapper();
        CarDTO carDTO = objectMapper.convertValue(carObject, CarDTO.class);
        return (DisplayCarDTO) carServ.updateEntity(carDTO);
    }

    @Override
    public void deleteById(Long id){
        carServ.deleteEntity(id);
    }

    @GetMapping("/vin/{vin}")
    public List<DisplayCarDTO> getCarByVin(@PathVariable String vin){
        return carServ.getCarByVin(vin);
    }
    @DeleteMapping("/delete/vin/{vin}")
    public void deleteCars(@PathVariable String vin){
        carServ.deleteCars(vin);
    }



}
