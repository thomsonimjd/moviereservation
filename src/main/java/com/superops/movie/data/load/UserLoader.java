package com.superops.movie.data.load;

import com.superops.movie.enums.UserType;
import com.superops.movie.user.User;
import com.superops.movie.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserLoader {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void addUsers() {
        List<User> users = Arrays.asList(addAdministratorUser(), addTheatreUser(), addCustomerUser());

        users = users.stream()
            .filter(u -> !userRepository.existsByUsername(u.getUsername()) && !userRepository.existsByEmailId(u.getEmailId()))
            .collect(Collectors.toList());

        userRepository.saveAll(users);
    }

    private User addAdministratorUser() {
        User user = new User();
        user.setUsername("The Admin");
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setEmailId("admin@superops.ai");
        user.setUserType(UserType.ADMINISTRATOR);
        return user;
    }

    public User addTheatreUser() {
        User user = new User();
        user.setUsername("PVR Cinemas");
        user.setUsername("pvr");
        user.setPassword(passwordEncoder.encode("pvr"));
        user.setEmailId("admin@pvrcinemas.com");
        user.setUserType(UserType.THEATRE);
        return user;
    }

    private User addCustomerUser() {
        User user = new User();
        user.setUsername("Thomson Ignesious");
        user.setUsername("thomson");
        user.setPassword(passwordEncoder.encode("thomson"));
        user.setEmailId("thomsonimjd@gmail.com");
        user.setUserType(UserType.CUSTOMER);
        return user;
    }
}
