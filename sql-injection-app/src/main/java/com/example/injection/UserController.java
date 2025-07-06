package com.example.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("sqli")
@RestController
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/user")
    public List<Map<String, Object>> getUser(@RequestParam String username) {
        // Intentionally vulnerable to SQL Injection
        String sql = "SELECT * FROM users WHERE username = '" + username + "'";
        return jdbcTemplate.queryForList(sql);
    }

    @GetMapping("/userSecure")
    public List<Map<String, Object>> getUserSecure(@RequestParam String username) {
        // Safe from SQL Injection
        return jdbcTemplate.queryForList("SELECT * FROM users WHERE username = ?",username);
    }
}