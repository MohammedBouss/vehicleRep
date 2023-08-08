package com.sue.cars.mappers;

import com.sue.cars.dtos.CarDTO;
import com.sue.cars.dtos.ModelBrandDTO;
import com.sue.cars.dtos.diplay.DisplayModelBrand;
import com.sue.cars.entity.Car;
import com.sue.cars.entity.ModelBrand;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelBrandMapper {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BrandMapper brandMapper;
    public DisplayModelBrand modelBrandToModelBrandDTO(ModelBrand modelBrand){
        DisplayModelBrand displayModelBrand = modelMapper.map(modelBrand, DisplayModelBrand.class);
        displayModelBrand.setBrandName(modelBrand.getBrand().getName());
        return displayModelBrand;
    }
    public ModelBrandDTO modelToDto(ModelBrand modelBrand){
        return modelMapper.map(modelBrand,ModelBrandDTO.class);
    }

    public ModelBrand modelBrandDTOToModelBrand(ModelBrandDTO modelBrandDTO){
        return modelMapper.map(modelBrandDTO, ModelBrand.class);
    }
}
