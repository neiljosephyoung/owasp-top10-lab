package com.example.brokenauth.model;

public class User {
    private String username;
    private String password; // hashed in secure version
    private int failedAttempts;
    private boolean locked;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.failedAttempts = 0;
        this.locked = false;
    }

    // getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getFailedAttempts() { return failedAttempts; }
    public void setFailedAttempts(int failedAttempts) { this.failedAttempts = failedAttempts; }

    public boolean isLocked() { return locked; }
    public void setLocked(boolean locked) { this.locked = locked; }
}

