package com.Nikita.AutoSalon.service;

import com.Nikita.AutoSalon.dto.BrandResponse;
import com.Nikita.AutoSalon.dto.CreateBrandRequest;
import com.Nikita.AutoSalon.dto.UpdateBrandRequest;
import com.Nikita.AutoSalon.entity.Brand;
import com.Nikita.AutoSalon.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    //Создание нового бренда
    public BrandResponse createBrand(CreateBrandRequest request){

        if (request.getName() == null || request.getName().isBlank()) {
            throw new RuntimeException("Название бренда обязательно");
        }

        if(brandRepository.existsByName(request.getName())){
            throw new RuntimeException("Такой брэнд уже существует");
        }

        Brand brand = new Brand();
        brand.setName(request.getName());

        Brand savedBrand = brandRepository.save(brand);

        BrandResponse response = new BrandResponse();

        response.setBrandId(savedBrand.getId());
        response.setBrand(savedBrand.getName());

        return response;
    }

    //Удаление брэнда
    public void deleteBrand(Long id){
        Brand brand = brandRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Брэнда с таким ID не найдено!"));

        brandRepository.delete(brand);
    }

    //Обновление данных бренда
    public BrandResponse updateBrand(Long id, UpdateBrandRequest request){
        Brand updateBrand = brandRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Такой бренд уже существует!"));

        if(!updateBrand.getName().equals(request.getName())&& brandRepository.existsByName(request.getName())){
            throw  new RuntimeException("Такой брэнд уже существует");
        }
        updateBrand.setName(request.getName());

        Brand savedUpdateBrand = brandRepository.save(updateBrand);

        BrandResponse response =new BrandResponse();
        response.setBrandId(savedUpdateBrand.getId());
        response.setBrand(savedUpdateBrand.getName());

        return response;
    }

    //Поиск бренда по названию
    public BrandResponse findByBrandName(String name){
        Brand brand = brandRepository.findByName(name)
                .orElseThrow(()-> new RuntimeException("Такого брэнда не существует!"));

        BrandResponse response = new BrandResponse();
        response.setBrandId(brand.getId());
        response.setBrand(brand.getName());

        return response;
    }

    //Поиск всех брэндов
    public List<BrandResponse> findAllBrands(){
        List<Brand> brands= brandRepository.findAll();
        List<BrandResponse> responses = new ArrayList<>();

        for(Brand brand : brands){
            BrandResponse response = new BrandResponse();
            response.setBrandId(brand.getId());
            response.setBrand(brand.getName());

            responses.add(response);
        }
        return  responses;
    }

    //Проверка существования брэнда
    public boolean existsBrand(String brand){
        return brandRepository.existsByName(brand);
    }
}
