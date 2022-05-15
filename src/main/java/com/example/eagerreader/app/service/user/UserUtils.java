package com.example.eagerreader.app.service.user;

import com.example.eagerreader.app.entity.User;
import com.example.eagerreader.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserUtils {

    private UserRepository userRepository;

    public User getCurrent() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String username = null;
        User user;
        if (null != securityContext.getAuthentication()) {

            user = (User) securityContext.getAuthentication().getPrincipal();
            username = user.getUsername();


        } else {
            return null;
        }
        return userRepository.findByEmail(username).orElseThrow(() ->
                new RuntimeException("Could not find user"));
    }
}