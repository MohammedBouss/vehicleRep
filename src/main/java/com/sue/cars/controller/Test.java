package com.sue.cars.controller;

import com.sue.cars.dtos.BrandDTO;
import com.sue.cars.dtos.CarDTO;
import com.sue.cars.dtos.CountryDTO;
import com.sue.cars.dtos.diplay.DisplayCarDTO;
import com.sue.cars.dtos.diplay.DisplayModelBrand;
import com.sue.cars.service.CountryService;
import com.sue.cars.service.ModelBrandService;
import com.sue.cars.service.brandService;
import com.sue.cars.service.carService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;


//@RequestMapping("car")
@Controller
public class Test {
    private final carService carServ;
    private final CountryService countryServ;
    private final ModelBrandService modelBrandService;
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

    @GetMapping("/viewCars/{offset}/{pageSize}")
    public String viewCars(Model model, @PathVariable int offset, @PathVariable int pageSize) {
//        ResponseEntity<List> response =
//                restTemplate.getForEntity("http://localhost:8084/api/car/all", List.class);
//        List<DisplayCarDTO> cars = response.getBody();
        List<DisplayCarDTO> cars = carServ.getAllCars(offset, pageSize);
        model.addAttribute("cars", cars);
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

    @GetMapping("/viewModels/{offset}/{pageSize}")
    public String viewModels(Model model, @PathVariable int offset, @PathVariable int pageSize) {
        List models = modelBrandService.getAllByPage(offset, pageSize);
        model.addAttribute("models", models);
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
