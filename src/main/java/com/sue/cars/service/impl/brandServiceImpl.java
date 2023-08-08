package com.sue.cars.service.impl;

import com.sue.cars.dtos.BrandDTO;
import com.sue.cars.entity.Brand;
import com.sue.cars.mappers.BrandMapper;
import com.sue.cars.repository.brandRepository;
import com.sue.cars.service.brandService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class brandServiceImpl implements brandService {

    @Autowired
    private brandRepository brandRep;
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<BrandDTO> getAll() {
        return brandRep.findAll().stream().map(brand -> brandMapper.BrandToBrandDTO(brand)).collect(Collectors.toList());
    }

    @Override
    public BrandDTO getById(Long id) {
        Optional<Brand> brand = brandRep.findById(id);
        if(brand.isPresent())
            return brandMapper.BrandToBrandDTO(brand.get());
        return null;    }

    @Override
    public BrandDTO getBrand(String name) {
        Optional<Brand> brand = brandRep.findByName(name);
        if(brand.isPresent())
            return brandMapper.BrandToBrandDTO(brand.get());
        System.out.println("not present ");
        return null;
    }

    @Override
    public BrandDTO addEntity(Object brandObject) {
        BrandDTO brandDTO = (BrandDTO) brandObject;
        brandRep.save(brandMapper.BrandDtoToBrand(brandDTO));
        return brandDTO;
    }

    @Override
    public BrandDTO updateEntity(Object brandObject) {
        BrandDTO brandDTO = (BrandDTO) brandObject;
        if(brandRep.findById(brandDTO.getId()).isPresent()){
             brandRep.save(brandMapper.BrandDtoToBrand(brandDTO));
             return brandDTO;
        }
        return null;
    }

    @Override
    public void deleteEntity(Long id) {
        Optional<Brand> brand = brandRep.findById(id);
        if (brand.isPresent())
            brandRep.delete(brand.get());
        else
            System.out.println("this brand not exist!");
    }
}
