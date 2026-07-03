package com.Nikita.AutoSalon.service;

import com.Nikita.AutoSalon.dto.CreateUserRequest;
import com.Nikita.AutoSalon.dto.UpdateUserRequest;
import com.Nikita.AutoSalon.dto.UserResponse;
import com.Nikita.AutoSalon.entity.User;
import com.Nikita.AutoSalon.enums.Roles;
import com.Nikita.AutoSalon.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //Создание пользователя
    public UserResponse createUser(CreateUserRequest request) throws Exception {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new Exception("Пользователь с таким email уже существует");
        }

        if(userRepository.existsByPhone(request.getPhone())){
            throw new Exception("Пользователь с таким номером телефона уже существует");
        }

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());
        user.setRole(Roles.CUSTOMER);
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse();

        response.setUserId(savedUser.getId());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setEmail(savedUser.getEmail());
        response.setPhone(savedUser.getPhone());

        return response;
    }

    //Удаление пользователя
    public void deleteUser(Long id) {
       User user = userRepository.findById(id)
               .orElseThrow(()-> new RuntimeException("Пользователь с таким id не найден"));

       userRepository.delete(user);
    }

    //Обновление данных пользователя
    public UserResponse updateUser(Long id, UpdateUserRequest request){

        User updateUser = userRepository.findById(id).orElseThrow(()->
                new RuntimeException("Пользователя с указанным ID не существует!"));

        if (!updateUser.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())){
            throw  new RuntimeException("Пользователь с таким email уже существует, введите другой email!");
        }

        if (!updateUser.getPhone().equals(request.getPhone()) && userRepository.existsByPhone(request.getPhone())){
            throw  new RuntimeException("Пользователь с таким номером телефона уже существует, введите другой номер телефона!");
        }

        updateUser.setFirstName(request.getFirstName());
        updateUser.setLastName(request.getLastName());
        updateUser.setEmail(request.getEmail());
        updateUser.setPhone(request.getPhone());
        updateUser.setPassword(request.getPassword());

        User saveUpdateUser = userRepository.save(updateUser);

        UserResponse response = new UserResponse();

        response.setUserId(saveUpdateUser.getId());
        response.setFirstName(saveUpdateUser.getFirstName());
        response.setLastName(saveUpdateUser.getLastName());
        response.setEmail(saveUpdateUser.getEmail());
        response.setPhone(saveUpdateUser.getPhone());
        return response;
    }

    //Поиск пользователя по email
    public UserResponse findByUserEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()->
                new RuntimeException("Пользователя с данным email не существует"));

        UserResponse response = new UserResponse();
        response.setUserId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        return response;
    }

    //Поиск всех пользователей
    public List<UserResponse> findAllUsers(){
         List<User> users = userRepository.findAll();
         List<UserResponse> responses = new ArrayList<>();

         for (User user: users){
             UserResponse response = new UserResponse();
             response.setUserId(user.getId());
             response.setFirstName(user.getFirstName());
             response.setLastName(user.getLastName());
             response.setEmail(user.getEmail());
             response.setPhone(user.getPhone());

             responses.add(response);
         }
         return  responses;
    }

    //Проверка существования пользователя по email
    public boolean existsUserByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    //Проверка существования пользователя по номеру телефона
    public boolean existsUserByPhone(String phone){
        return userRepository.existsByPhone(phone);
    }

}
