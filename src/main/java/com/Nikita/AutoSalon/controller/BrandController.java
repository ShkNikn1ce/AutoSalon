package com.Nikita.AutoSalon.controller;

import com.Nikita.AutoSalon.dto.response.BrandResponse;
import com.Nikita.AutoSalon.dto.request.CreateBrandRequest;
import com.Nikita.AutoSalon.dto.request.UpdateBrandRequest;
import com.Nikita.AutoSalon.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name="Бренды", description = "API для управления брендами")
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;

    @Operation(
            summary = "Создать новый бренд",
            description = "Создает запись о новом бренде в системе"
    )
    @ApiResponses(value={
            @ApiResponse(responseCode = "201", description = "Бренд успешно создан"),
            @ApiResponse(responseCode = "400", description = "Неверные данные запроса"),
            @ApiResponse(responseCode = "500", description = "Такой Бренд уже существует")
    })

    @PostMapping
    public BrandResponse createBrand(@RequestBody CreateBrandRequest request) throws RuntimeException {
        return brandService.createBrand(request);
    }

    @Operation(
            summary = "Вывести список всех брендов",
            description = "Выводит список всех автомобилей"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вывод списка всех брендов"),
            @ApiResponse(responseCode = "404", description = "Указанный путь не найден")
    }

    )
    @GetMapping
    public List<BrandResponse> getAllBrands() {
        return brandService.findAllBrands();
    }

    @Operation(
            summary = "Поиск бренда по названию",
            description = "Возвращает информацию о бренде по его названию"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Бренд найден"),
            @ApiResponse(responseCode = "404", description = "Бренда с таким названием не найден")
    }
    )
    @GetMapping("/search")
    public BrandResponse getBrandByName(@RequestParam String brandName) {
        return brandService.findByBrandName(brandName);
    }

    @Operation(
            summary = "Удалить бренд",
            description = "Удаляет юренд из системы по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Бренд успешно удален"),
            @ApiResponse(responseCode = "404", description = "Бренд с таким ID не найден")
    })
    @DeleteMapping("/{brandId}")
    public void deleteBrand(@PathVariable Long brandId) {
        brandService.deleteBrand(brandId);
    }

    @Operation(
            summary = "Обновить информацию о бренде",
            description = "Обновляет данные существующего бренда по его ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация обновлена"),
            @ApiResponse(responseCode = "500", description = "Неверные данные запроса"),
            @ApiResponse(responseCode = "500", description = "Бренд с таким ID не найден")
    })
    @PutMapping("/{brandId}")
    public BrandResponse updateBrand(@PathVariable Long brandId, @RequestBody UpdateBrandRequest request) {
        return brandService.updateBrand(brandId, request);
    }




}
