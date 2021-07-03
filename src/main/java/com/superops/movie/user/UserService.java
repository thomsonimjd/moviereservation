package com.superops.movie.user;

import com.superops.movie.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public User save(User user) {
        user.setUserType(UserType.CUSTOMER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmailId(String emailId) {
        return userRepository.existsByEmailId(emailId);
    }

    public boolean existsById(UUID userId) {
        return userRepository.existsById(userId);
    }

    @Transactional
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }


}
