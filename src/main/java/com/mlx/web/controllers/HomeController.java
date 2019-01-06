package com.mlx.web.controllers;

import com.mlx.domain.entities.Ad;
import com.mlx.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private static final int ITEMS_PER_PAGE = 3;

    private final AdService adService;

    @Autowired
    public HomeController(AdService adService) {
        this.adService = adService;
    }

    @GetMapping("/")
    public String home(Model model, @RequestParam(required = false) Integer page) {
        if (page == null) {
            page = 1;
        }
        Integer allAdsSize = this.adService.findAll().size();
        List<Ad> ads = this.adService.findAll().stream()
                .skip((page - 1) * ITEMS_PER_PAGE)
                .limit(ITEMS_PER_PAGE)
                .collect(Collectors.toList());
        model.addAttribute("ads", ads);
        model.addAttribute("pages", Math.ceil(allAdsSize / ITEMS_PER_PAGE));
        model.addAttribute("currentPage", page);

        return "home/index";
    }

    @GetMapping("/about")
    public String about() {
        return "home/about";
    }
}
