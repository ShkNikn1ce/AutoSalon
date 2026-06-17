package com.Nikita.AutoSalon.service;

import com.Nikita.AutoSalon.entity.User;
import com.Nikita.AutoSalon.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //Создание пользователя
    public User createUser(User user) throws Exception {

        if(userRepository.existsByEmail(user.getEmail())){
            throw new Exception("Пользователь с таким email уже существует");
        }

        if(userRepository.existsByPhone(user.getPhone())){
            throw new Exception("Пользователь с таким номером телефона уже существует");
        }

        return userRepository.save(user);
    }

    //Удаление пользователя
    public void deleteUser(Long id) {
       User user = userRepository.findById(id)
               .orElseThrow(()-> new RuntimeException("Пользователь с таким id не найден"));

       userRepository.delete(user);
    }

    //Поиск пользователя по email
    public User findByUserEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->
                new RuntimeException("Пользователь с таким email не найден"));

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
