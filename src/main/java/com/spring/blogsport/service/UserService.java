package com.spring.blogsport.service;

import com.spring.blogsport.model.User;
import com.spring.blogsport.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Registers a new user with encrypted password
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Finds a user by email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Returns a user by ID
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Updates user information
    public User updateUser(Long id, User updated) {
        User user = findById(id);
        user.setName(updated.getName());
        user.setEmail(updated.getEmail());
        if (updated.getPassword() != null && !updated.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(updated.getPassword()));
        }
        user.setProfileImage(updated.getProfileImage());
        return userRepository.save(user);
    }

    // Deletes a user by ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Lists all users (for admin only)
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
