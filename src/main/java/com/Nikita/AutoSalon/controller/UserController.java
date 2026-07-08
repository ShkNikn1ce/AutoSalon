package com.Nikita.AutoSalon.controller;

import com.Nikita.AutoSalon.dto.request.CreateUserRequest;
import com.Nikita.AutoSalon.dto.request.UpdateUserRequest;
import com.Nikita.AutoSalon.dto.response.UserResponse;
import com.Nikita.AutoSalon.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/new-user")
    public UserResponse createUser(@RequestBody CreateUserRequest request) throws Exception {
        return userService.createUser(request);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest request) throws Exception{
         return  userService.updateUser(userId, request);
    }

    @GetMapping
    public UserResponse getUserByEmail(@RequestParam String userEmail){
        return userService.findByUserEmail(userEmail);
    }

    @GetMapping
    public List<UserResponse> getAllUsers(){
        return userService.findAllUsers();
    }
}
