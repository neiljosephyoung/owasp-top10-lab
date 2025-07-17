package com.example.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccessController {

    @GetMapping("/admin/dashboard")
    public String secureAdminDashboard() {
        return "✅ Admin dashboard (secured)";
    }

    // Intentionally left public for broken access demo
    @GetMapping("/admin/admin-dash")
    public String brokenAdminDashboard() {
        return "❌ Admin dashboard (no auth check!)";
    }
}
