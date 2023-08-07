package com.sue.cars.service;

import com.sue.cars.dtos.BrandDTO;
import com.sue.cars.entity.Brand;

import java.util.List;

public interface brandService extends ServiceCRUD{
     BrandDTO getBrand(String name);
}
