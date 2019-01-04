package com.mlx.web.controllers;

import com.mlx.domain.entities.Ad;
import com.mlx.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final AdService adService;

    @Autowired
    public HomeController(AdService adService) {
        this.adService = adService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Ad> allAds = this.adService.findAll();
        model.addAttribute("ads", allAds);

        return "home/index";
    }

    @GetMapping("/about")
    public String about() {
        return "home/about";
    }
}
