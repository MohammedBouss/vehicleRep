package com.sue.cars.controller;

import com.sue.cars.dtos.BrandDTO;
import com.sue.cars.dtos.CarDTO;
import com.sue.cars.dtos.CountryDTO;
import com.sue.cars.dtos.diplay.DisplayCarDTO;
import com.sue.cars.dtos.diplay.DisplayModelBrand;
import com.sue.cars.entity.Car;
import com.sue.cars.entity.ModelBrand;
import com.sue.cars.mappers.CarMapper;
import com.sue.cars.mappers.ModelBrandMapper;
import com.sue.cars.service.CountryService;
import com.sue.cars.service.ModelBrandService;
import com.sue.cars.service.brandService;
import com.sue.cars.service.carService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


//@RequestMapping("car")
@Controller
public class Test {
    private final carService carServ;
    private final CountryService countryServ;
    private final ModelBrandService modelBrandService;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private ModelBrandMapper modelBrandMapper;
    @Autowired
    private brandService brandServ;
    private final RestTemplate restTemplate;

    public Test(carService carServ, RestTemplate restTemplate, CountryService countryServ, ModelBrandService modelBrandService) {
        this.carServ = carServ;
        this.restTemplate = restTemplate;
        this.countryServ = countryServ;
        this.modelBrandService = modelBrandService;
    }

    @GetMapping("/hi")
    public String hello() {
        String s = "h";
        return "hi";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/viewCars")
    public String viewCars(Model model, @RequestParam("offset") Optional<Integer> offset, @RequestParam("pageSize") Optional<Integer> pageSize) {
//        ResponseEntity<List> response =
//                restTemplate.getForEntity("http://localhost:8084/api/car/all", List.class);
//        List<DisplayCarDTO> cars = response.getBody();
        int currentPage = offset.orElse(1);
        int pgSize = pageSize.orElse(20);
        Page<Car> carPage = carServ.getCarsByPage(currentPage-1, pgSize);
        List<DisplayCarDTO> cars = carPage.getContent().stream().map(car -> carMapper.carToDisCarDto(car)).collect(Collectors.toList());
        model.addAttribute("cars", cars);
        model.addAttribute("carPage", carPage);
        int totalPages = carPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(offset.orElse(1),offset.orElse(1)+ 9)
                    .boxed()
                    .collect(Collectors.toList());
            System.out.println("nbr of pages :"+pageNumbers);

            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "cars";
    }

    @GetMapping("/searchCars")
    public String searchCars(Model model, @RequestParam(name = "vin") String vin) {
        List<DisplayCarDTO> cars = carServ.getCarByVin(vin);
        model.addAttribute("results", cars);
        return "searchCars";
    }

    @GetMapping("/searchBrands")
    public String searchBrands(Model model, @RequestParam(name = "name") String name) {
        BrandDTO brand = brandServ.getBrand(name);
        model.addAttribute("result", brand);
        return "searchBrand";
    }

    @GetMapping("/searchModelBrandsByBrandAndYear")
    public String getModelBrandsByBrandAndYear(Model model, @RequestParam(name = "brandName") Optional<String> brandName,@RequestParam(name = "modelYear") Optional<Integer> modelYear) {
        System.out.println("brand name : "+brandName.get()+"model year :"+modelYear.get());
        BrandDTO brand = brandServ.getBrand(brandName.orElse("tesla"));
//        System.out.println("brand name "+brand);
        List<DisplayModelBrand> displayModelBrands = modelBrandService.getByModelYearAndBrand(brand,modelYear.orElse(2018));
        model.addAttribute("displayModelBrands", displayModelBrands);
        return "searchCarByModelBrand";
    }

//    @GetMapping("/searchCarByModelBrand")
//    public String getCarsByModelBrands(Model model, @RequestParam(name = "name") String name) {
//        DisplayModelBrand modelBrand = (DisplayModelBrand) model.getAttribute("modelbrand");
//        BrandDTO brand = brandServ.getBrand(name);
//        model.addAttribute("result", brand);
//        return "searchCarByModelBrand";
//    }

    @GetMapping("/searchModel")
    public String searchModel(Model model, @RequestParam(name = "modelName") String name) {
        DisplayModelBrand modelBrand = modelBrandService.getModelBrand(name);
        model.addAttribute("result", modelBrand);
        return "searchModelBrand";
    }

    @GetMapping("/viewBrands")
    public String viewBrands(Model model) {
        ResponseEntity<List> response =
                restTemplate.getForEntity("http://localhost:8084/api/brand/all", List.class);
        List<BrandDTO> brands = response.getBody();
        model.addAttribute("brands", brands);
        return "brands";
    }

    @GetMapping("/viewModels")
    public String viewModels(Model model, @RequestParam Optional<Integer> currentPage, @RequestParam Optional<Integer> pageSize) {
        Page<ModelBrand> modelBrandPage = modelBrandService.getModelBrandPage(currentPage.orElse(1)-1, pageSize.orElse(20));
        List<DisplayModelBrand> models = modelBrandPage.getContent().stream().map(modelBrand -> modelBrandMapper.modelBrandToModelBrandDTO(modelBrand)).collect(Collectors.toList());
        List<BrandDTO> brands =brandServ.getAll();
        List<Integer> modelYears = modelBrandService.gerDistinctModelYear();
        model.addAttribute("models", models);
        model.addAttribute("brands", brands);
        model.addAttribute("modelYears", modelYears);
        model.addAttribute("modelBrandPage", modelBrandPage);
        int totalPages = modelBrandPage.getTotalPages();
        System.out.println("total model brand pages is :"+totalPages);
        if(totalPages > 0){
            List<Integer> pageNumbers;
            if(totalPages > currentPage.orElse(1)+ 9){
                pageNumbers = IntStream.rangeClosed(currentPage.orElse(1), currentPage.orElse(1) + 9)
                        .boxed()
                        .collect(Collectors.toList());
            }else {
                int start = currentPage.orElse(1);
                if(currentPage.orElse(1) == totalPages){
                    start = totalPages - 5;
                }
                pageNumbers = IntStream.rangeClosed(start, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
            }
            model.addAttribute("pageNumbers",pageNumbers);

        }
        return "models";
    }

    @GetMapping("/viewCities")
    public String viewCities(Model model) {
        ResponseEntity<List> response =
                restTemplate.getForEntity("http://localhost:8084/api/city/all", List.class);
        List cities = response.getBody();
        List<CountryDTO> countryDTOS = countryServ.getAll();
        model.addAttribute("cities", cities);
        model.addAttribute("countries",countryDTOS);
        return "cities";
    }
}
