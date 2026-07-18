package com.Nikita.AutoSalon.security.user;

import com.Nikita.AutoSalon.entity.User;
import com.Nikita.AutoSalon.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User user =userRepository.findByEmail(email)
               .orElseThrow(()->
                       new UsernameNotFoundException("Пользователь не найден"));

       return new CustomUserDetails(user);
    }
}
