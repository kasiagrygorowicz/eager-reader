package com.example.eagerreader.service;

import com.example.eagerreader.dto.CreateUserDto;
import com.example.eagerreader.entity.Role;
import com.example.eagerreader.entity.User;
import com.example.eagerreader.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    public void addUser(CreateUserDto user) {
        log.info("New user email :{} password {}", user.getEmail(), user.getPassword());
        User newUser = Mapper.map(user);
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
    }


    private class Mapper {
        private static User map(CreateUserDto user) {
            return new User(
                    user.getEmail(),
                    user.getPassword(),
                    Role.user
            );
        }
    }
}
