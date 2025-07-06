package com.example.brokenauth.controller;

import com.example.brokenauth.model.User;
import com.example.brokenauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    // Vulnerable login (plaintext password check, no lockout)
    @PostMapping("/loginVulnerable")
    public ResponseEntity<String> loginVulnerable(@RequestBody LoginRequest req) {
        User user = userService.findByUsername(req.getUsername());
        if (user == null || !user.getPassword().equals(req.getPassword())) {  // insecure: plain equals
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        return ResponseEntity.ok("Login successful (vulnerable)");
    }

    // Secure login (hashed password, lockout after 3 failed attempts)
    @PostMapping("/loginSecure")
    public ResponseEntity<String> loginSecure(@RequestBody LoginRequest req) {
        User user = userService.findByUsername(req.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        if (userService.isLocked(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Account locked due to multiple failed attempts");
        }
        if (!userService.checkPassword(user, req.getPassword())) {
            userService.increaseFailedAttempts(user);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        userService.resetFailedAttempts(user);
        return ResponseEntity.ok("Login successful (secure)");
    }

    // DTO for login
    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}

