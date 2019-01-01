package com.mlx.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ads")
public class AdController {

    @GetMapping("/create")
    public String create() {
        return "ad/create";
    }
}
