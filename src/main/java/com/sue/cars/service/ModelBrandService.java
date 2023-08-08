package com.sue.cars.service;

import com.sue.cars.dtos.BrandDTO;
import com.sue.cars.dtos.ModelBrandDTO;
import com.sue.cars.dtos.diplay.DisplayModelBrand;
import com.sue.cars.entity.ModelBrand;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ModelBrandService extends ServiceCRUD{
    DisplayModelBrand getModelBrand(String name);
    List getAllByPage(int offset, int pageSize);
    List<DisplayModelBrand> getByModelYearAndBrand(BrandDTO brandDTO, int modelYear);
    List<Integer> gerDistinctModelYear();
    Page<ModelBrand> getModelBrandPage(int currentPage, int pageSize);
}
