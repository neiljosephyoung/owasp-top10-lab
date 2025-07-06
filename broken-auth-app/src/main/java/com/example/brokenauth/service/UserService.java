package com.example.brokenauth.service;

import com.example.brokenauth.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class UserService {
    private Map<String, User> users = new HashMap<>();
    private final int MAX_FAILED_ATTEMPTS = 3;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostConstruct
    public void init() {
        // Add two users: passwords are hashed for secure version
        users.put("alice", new User("alice", "password123"));
        users.put("bob", new User("bob", passwordEncoder.encode("qwerty")));
    }

    public User findByUsername(String username) {
        return users.get(username);
    }

    public boolean isLocked(User user) {
        return user.isLocked();
    }

    public void increaseFailedAttempts(User user) {
        int attempts = user.getFailedAttempts() + 1;
        user.setFailedAttempts(attempts);
        if (attempts >= MAX_FAILED_ATTEMPTS) {
            user.setLocked(true);
        }
    }

    public void resetFailedAttempts(User user) {
        user.setFailedAttempts(0);
        user.setLocked(false);
    }

    public boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}
