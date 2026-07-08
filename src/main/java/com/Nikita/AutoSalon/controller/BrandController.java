package com.Nikita.AutoSalon.controller;

import com.Nikita.AutoSalon.dto.response.BrandResponse;
import com.Nikita.AutoSalon.dto.request.CreateBrandRequest;
import com.Nikita.AutoSalon.dto.request.UpdateBrandRequest;
import com.Nikita.AutoSalon.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;

    @PostMapping()
    public BrandResponse createBrand(@RequestBody CreateBrandRequest request) throws RuntimeException {
        return brandService.createBrand(request);
    }

    @DeleteMapping("/{brandId}")
    public void deleteBrand(@PathVariable Long brandId) {
        brandService.deleteBrand(brandId);
    }


    @PutMapping("/{brandId}")
    public BrandResponse updateBrand(@PathVariable Long brandId, @RequestBody UpdateBrandRequest request) {
        return brandService.updateBrand(brandId, request);
    }

    @GetMapping
    public List<BrandResponse> getAllBrands() {
        return brandService.findAllBrands();
    }

    @GetMapping("/search")
    public BrandResponse getBrandByName(@RequestParam String brandName) {
        return brandService.findByBrandName(brandName);
    }


}
