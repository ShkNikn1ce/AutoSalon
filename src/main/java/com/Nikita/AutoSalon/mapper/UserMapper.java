package com.Nikita.AutoSalon.mapper;

import com.Nikita.AutoSalon.dto.UserResponse;
import com.Nikita.AutoSalon.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toResponse(User request){
        UserResponse response = new UserResponse();

        response.setUserId(request.getId());
        response.setFirstName(request.getFirstName());
        response.setLastName(request.getLastName());
        response.setEmail(request.getEmail());
        response.setPhone(request.getPhone());

        return response;
    }
}
