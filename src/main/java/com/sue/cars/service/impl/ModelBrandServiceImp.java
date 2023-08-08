package com.sue.cars.service.impl;

import com.sue.cars.dtos.BrandDTO;
import com.sue.cars.dtos.ModelBrandDTO;
import com.sue.cars.dtos.diplay.DisplayModelBrand;
import com.sue.cars.entity.Brand;
import com.sue.cars.entity.ModelBrand;
import com.sue.cars.mappers.BrandMapper;
import com.sue.cars.mappers.ModelBrandMapper;
import com.sue.cars.repository.brandRepository;
import com.sue.cars.repository.modelBrandRepository;
import com.sue.cars.service.ModelBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ModelBrandServiceImp implements ModelBrandService {

    @Autowired
    private modelBrandRepository modelBrandRep;
    @Autowired
    private brandRepository brandRep;
    @Autowired
    private ModelBrandMapper modelBrandMapper;
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<DisplayModelBrand> getAll() {
        return modelBrandRep.findAll().stream().
                map(modelBrand -> modelBrandMapper.modelBrandToModelBrandDTO(modelBrand))
                .collect(Collectors.toList());
    }

    @Override
    public DisplayModelBrand getModelBrand(String name) {
        Optional<ModelBrand> modelBrand = modelBrandRep.findByName(name);
        if(modelBrand.isPresent()){
            return modelBrandMapper.modelBrandToModelBrandDTO(modelBrand.get());
        }
        return null;
    }

    @Override
    public List getAllByPage(int offset, int pageSize) {
        return modelBrandRep.findAll(PageRequest.of(offset, pageSize)).stream().
                map(modelBrand -> modelBrandMapper.modelBrandToModelBrandDTO(modelBrand))
                .collect(Collectors.toList());
    }

    @Override
    public List<DisplayModelBrand> getByModelYearAndBrand(BrandDTO brandDTO, int modelYear) {
        return modelBrandRep.findModelBrandByBrandAndModelYear(
            brandMapper.BrandDtoToBrand(brandDTO),
                modelYear
        ).stream().map(modelBrand -> modelBrandMapper.modelBrandToModelBrandDTO(modelBrand)).collect(Collectors.toList());
    }

    @Override
    public List<Integer> gerDistinctModelYear() {
        return modelBrandRep.getDistinctModelYear();
    }

    @Override
    public Page<ModelBrand> getModelBrandPage(int currentPage, int pageSize) {
        return modelBrandRep.findAll(PageRequest.of(currentPage, pageSize));
    }

    @Override
    public DisplayModelBrand getById(Long id) {
        Optional<ModelBrand> modelBrand = modelBrandRep.findById(id);
        if(modelBrand.isPresent()){
            return modelBrandMapper.modelBrandToModelBrandDTO(modelBrand.get());
        }
        return null;
    }

    @Override
    public DisplayModelBrand addEntity(Object modelBrandObject) {
        ModelBrandDTO modelBrandDTO = (ModelBrandDTO) modelBrandObject;
        Optional<Brand> brand  = brandRep.findById(modelBrandDTO.getBrandId());
        if(brand.isPresent()){
            ModelBrand modelBrand = modelBrandMapper.modelBrandDTOToModelBrand(modelBrandDTO);
            modelBrand.setBrand(brand.get());
            return modelBrandMapper.modelBrandToModelBrandDTO(modelBrandRep.save(modelBrand));
        }
        return null;
    }

    @Override
    public DisplayModelBrand updateEntity(Object modelBrandObject) {
        ModelBrandDTO modelBrandDTO = (ModelBrandDTO) modelBrandObject;
        if(modelBrandRep.findById(modelBrandDTO.getId()).isPresent()){
             ModelBrand modelBrand = modelBrandMapper.modelBrandDTOToModelBrand(modelBrandDTO);
             modelBrand.setBrand(brandRep.findById(modelBrandDTO.getBrandId()).get());
            return modelBrandMapper.modelBrandToModelBrandDTO(modelBrandRep.save(modelBrand));
        }
        return null;
    }

    @Override
    public void deleteEntity(Long id) {
        Optional<ModelBrand> modelBrand = modelBrandRep.findById(id);
        if(modelBrand.isPresent()){
            modelBrandRep.delete(modelBrand.get());
        }
    }
}
