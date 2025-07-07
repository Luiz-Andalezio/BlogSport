package com.spring.blogsport.service;

import com.spring.blogsport.model.User;
import com.spring.blogsport.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findByRole(String role) {
        return userRepository.findByRole(User.Role.valueOf(role));
    }

    // Register an user with role
    public void createUserWithRole(User user, String role) {
        if (user.getEmail() == null || user.getEmail().isBlank())
            throw new IllegalArgumentException("Email is required");
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            throw new IllegalArgumentException("Email already exists");
        if (user.getPassword() == null || user.getPassword().length() < 6)
            throw new IllegalArgumentException("Password must be at least 6 characters");
        user.setRole(User.Role.valueOf(role));
        userRepository.save(user);
    }

    // Update a new user with role
    public User updateUser(Long id, User updated, String role) {
        User user = findById(id);
        if (updated.getName() == null || updated.getName().length() < 2)
            throw new IllegalArgumentException("Name must have at least 2 characters");
        if (updated.getEmail() == null || updated.getEmail().isBlank())
            throw new IllegalArgumentException("Email is required");
        if (!user.getEmail().equals(updated.getEmail()) && userRepository.findByEmail(updated.getEmail()).isPresent())
            throw new IllegalArgumentException("Email already exists");
        user.setName(updated.getName());
        user.setEmail(updated.getEmail());
        user.setBirthDate(updated.getBirthDate());
        user.setRole(User.Role.valueOf(role));
        user.setProfileImage(updated.getProfileImage());
        return userRepository.save(user);
    }

    // Registers a new user with encrypted password
    public User registerUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank())
            throw new IllegalArgumentException("Email is required");
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            throw new IllegalArgumentException("Email already exists");
        if (user.getPassword() == null || user.getPassword().length() < 6)
            throw new IllegalArgumentException("Password must be at least 6 characters");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole(User.Role.USER);
        }
        return userRepository.save(user);
    }

    // Changes the password of a user
    public void changePassword(Long id, String newPassword) {
        if (newPassword == null || newPassword.length() < 6)
            throw new IllegalArgumentException("Password must be at least 6 characters");
        User user = findById(id);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
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
        if (updated.getName() == null || updated.getName().length() < 2)
            throw new IllegalArgumentException("Name must have at least 2 characters");
        if (updated.getEmail() == null || updated.getEmail().isBlank())
            throw new IllegalArgumentException("Email is required");
        if (!user.getEmail().equals(updated.getEmail()) && userRepository.findByEmail(updated.getEmail()).isPresent())
            throw new IllegalArgumentException("Email already exists");
        user.setName(updated.getName());
        user.setEmail(updated.getEmail());
        user.setBirthDate(updated.getBirthDate());
        user.setProfileImage(updated.getProfileImage());
        return userRepository.save(user);
    }

    // Deletes a user by ID
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id))
            throw new IllegalArgumentException("User not found");
        userRepository.deleteById(id);
    }

    // Lists all users (for admin only)
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
