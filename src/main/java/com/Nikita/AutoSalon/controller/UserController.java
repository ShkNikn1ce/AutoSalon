package com.Nikita.AutoSalon.controller;

import com.Nikita.AutoSalon.dto.request.CreateUserRequest;
import com.Nikita.AutoSalon.dto.request.UpdateUserRequest;
import com.Nikita.AutoSalon.dto.response.UserResponse;
import com.Nikita.AutoSalon.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name="Пользователи", description ="API для управления пользователями")
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Создать нового пользователя",
            description = "Создает запись о новом пользователе в системе"
    )
    @ApiResponses(value ={
         @ApiResponse(responseCode = "201", description = "Пользователь успешно создан"),
         @ApiResponse(responseCode = "400", description = "Неверные данные запроса"),
         @ApiResponse(responseCode = "500", description = "Такой пользователь уже существует")
    })
    @PostMapping
    public UserResponse createUser(@RequestBody CreateUserRequest request) throws Exception {
        return userService.createUser(request);
    }

    @Operation(
            summary = "Вывести список всех пользователей",
            description = "Выводит список всех пользователей"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вывод списка всех пользователей"),
            @ApiResponse(responseCode = "404", description = "Указанный путь не найден")
    })
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.findAllUsers();
    }


    @Operation(
            summary = "Поиск пользователя по email",
            description = "Возвращает информацию о пользователе по его email"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь с таким email не найден")
    })
    @GetMapping("/{userEmail}")
    public UserResponse getUserByEmail(@PathVariable String userEmail){
        return userService.findByUserEmail(userEmail);
    }

    @Operation(
            summary = "Удалить пользователя",
            description = "Удаляет пользователя из системы по ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно удален"),
            @ApiResponse(responseCode = "404", description = "Пользователь с таким ID не найден")
    })

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }

    @Operation(
            summary = "Обновить информацию о пользователе",
            description = "Обновляет данные существующего пользователя по его ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация обновлена"),
            @ApiResponse(responseCode = "500", description = "Неверные данные запроса"),
            @ApiResponse(responseCode = "500", description = "Пользователь с таким ID не найден")
    })

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest request) throws Exception{
         return  userService.updateUser(userId, request);
    }

}
