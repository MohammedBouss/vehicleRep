package com.sue.cars.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sue.cars.dtos.*;
import com.sue.cars.dtos.diplay.DisplayCarDTO;
import com.sue.cars.dtos.diplay.DisplayModelBrand;
import com.sue.cars.entity.*;
import com.sue.cars.mappers.CarMapper;
import com.sue.cars.mappers.ModelBrandMapper;
import com.sue.cars.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    @Autowired
    private electricUtilityService electricUtilityService;
    @Autowired
    private CityService cityService;
    @Autowired
    private StateService stateService;
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
                restTemplate.getForEntity("http://localhost:8080/api/brand/all", List.class);
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
                restTemplate.getForEntity("http://localhost:8080/api/city/all", List.class);
        List cities = response.getBody();
        List<CountryDTO> countryDTOS = countryServ.getAll();
        model.addAttribute("cities", cities);
        model.addAttribute("countries",countryDTOS);
        return "cities";
    }
    @GetMapping("/addCar")
    public String showFormCar(Model model)
    {
        CarDTO carDTO =new CarDTO();
//        List<ElectricUtilityDTO> electricUtilityDTOS =electricUtilityService.getAll();
        model.addAttribute("car",carDTO);
        model.addAttribute("electricTypes", ElectricVehicleType.values());
        model.addAttribute("eligibilitys", Eligibility.values());
        model.addAttribute("eletctricUtilitys",electricUtilityService.getAll());
        model.addAttribute("modelBrands",modelBrandService.getAll());
        model.addAttribute("citys",cityService.getAll());

        return "addCar";
    }
    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addCar(CarDTO carDTO) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        CarDTO carDTO1 = objectMapper.convertValue(carDTO, CarDTO.class);
        carServ.addEntity(carDTO);
        return "redirect:/viewCars";
    }
    @GetMapping(path = "/delete/{vin}")
    public String deleteCar(@PathVariable String vin) {
        carServ.deleteCars(vin);
        return "redirect:/viewCars";
    }
    @GetMapping(path = "/update/{id}")
    public String updateCar(@PathVariable Long id,Model model) {
        CarDTO carDTO= (CarDTO) carServ.getById(id);
        model.addAttribute("cardto",carDTO);
        model.addAttribute("citys",cityService.getAll());
        model.addAttribute("electricTypes",ElectricVehicleType.values());
        model.addAttribute("eligibilitys",Eligibility.values());
        model.addAttribute("modelBrands",modelBrandService.getAll());
        model.addAttribute("electricUtilitys",electricUtilityService.getAll());
        model.addAttribute("defaultCity",cityService.getById(carDTO.getId()));
        model.addAttribute("defaultElectricType",carDTO.getElectricType());
        model.addAttribute("defaultEligibility",carDTO.getEligibility());
        model.addAttribute("defaultModelBrand",carDTO.getModelBrandId());
        model.addAttribute("defaultElectricUtility",carDTO.getElectricUtilityId());
        model.addAttribute("pageTitle","Edit Car: "+id);
        return "editCar";
    }
    @PostMapping(path = "/update",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateCar(CarDTO carDTO) {
        carServ.updateEntity(carDTO);
        return "redirect:/viewCars";
    }

    @GetMapping("/addCountry")
    public String showFormCountry(Model model)
    {
        model.addAttribute("country",new CountryDTO());
        return "addCountry";
    }
    @PostMapping( path = "/country/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addCountry(CountryDTO countryDTO)
    {
        countryServ.addEntity(countryDTO);
        return "cars";
    }

    @GetMapping("/addCity")
    public String showFormCity(Model model,@RequestParam Optional<String> countryName)
    {
        String nameC = countryName.orElse("Sedgwick");
        List<String> states = stateService.findStateByCountryName(nameC);
        model.addAttribute("city",new CityDTO());
        model.addAttribute("countrys",countryServ.getAll());
        model.addAttribute("states",states);
        return "addCity";
    }
    @GetMapping("/Country/{id}")
    public String loadState(@PathVariable(value = "id") long id)
    {
       Gson gson=new Gson();
       return gson.toJson(stateService.findByCountry(id));
    }
    @GetMapping("/addModel")
    public String showFormModel(Model model)
    {
        model.addAttribute("modelBrand",new ModelBrandDTO());
        model.addAttribute("brands",brandServ.getAll());
        return "addModelBrand";
    }
    @PostMapping(path = "/modelBrand/save" , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addModelBrand(ModelBrandDTO modelBrandDTO)
    {
        modelBrandService.addEntity(modelBrandDTO);
        return "redirect:/viewModels";
    }
    @GetMapping(path = "/delete/modelBrand/{id}")
    public String deleteModelBrand(@PathVariable Long id) {
        modelBrandService.deleteEntity(id);
        return "redirect:/viewModels";
    }
    @GetMapping(path = "/update/modelBrand/{id}")
    public String updateModelBrand(@PathVariable(value = "id") Long id,Model model) {
        ModelBrandDTO modelBrandDTO= (ModelBrandDTO) modelBrandService.getById(id);
        model.addAttribute("modelBrandDto",modelBrandDTO);
        model.addAttribute("Brands",brandServ.getAll());
        model.addAttribute("defaultBrand",brandServ.getById(modelBrandDTO.getId()));
        return "editModelBrand";
    }
    @PostMapping(path = "/modelBrand/update",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateModelBrand(ModelBrandDTO modelBrandDTO) {
        modelBrandService.updateEntity(modelBrandDTO);
        return "redirect:/viewCars";
    }
}
