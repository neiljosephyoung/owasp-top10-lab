package com.example.xss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/submit")
    public String submitComment(@RequestParam String comment, Model model) {
        model.addAttribute("userComment", comment);
        return "fragments :: commentDisplayFragment";
    }

    @PostMapping("/submitSafe")
    public String submitSafeComment(@RequestParam String comment, Model model) {
        model.addAttribute("userComment", comment);
        return "fragments :: safeCommentDisplayFragment";
    }


}