package com.mlx.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home/index";
    }

    @GetMapping("/about")
    public String about() {
        return "home/about";
    }
}