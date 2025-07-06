package com.example.brokenauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // optional, depends on your POSTs
                .authorizeRequests()
                .anyRequest().permitAll()  // Allow all endpoints
                .and()
                .httpBasic().disable();  // Disable default Basic Auth

        return http.build();
    }
}
