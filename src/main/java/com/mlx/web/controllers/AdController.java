package com.mlx.web.controllers;

import com.mlx.domain.models.binding.AdSubmitBindingModel;
import com.mlx.service.AdService;
import com.mlx.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ads")
public class AdController {

    private final CategoryService categoryService;

    private final AdService adService;

    @Autowired
    public AdController(CategoryService categoryService, AdService adService) {
        this.categoryService = categoryService;
        this.adService = adService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String create(Model model) {
        List<String> selectedCategories = new ArrayList<>();

        model.addAttribute("selectedCategories", selectedCategories);
        model.addAttribute("categories", this.categoryService.findAll());

        return "ad/create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/create", produces = "application/json")
    public @ResponseBody Map<String, String> createProcess(Model model,
                                                           @Valid @ModelAttribute AdSubmitBindingModel bindingModel,
                                                           BindingResult bindingResult,
                                                           Principal principal,
                                                           RedirectAttributes redirectAttributes,
                                                           HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        if (errors.size() > 0) {
            response.setStatus(422);
            map.put("error", errors.get(errors.size() - 1));
            return map;
        }
        this.adService.create(bindingModel, principal);

        redirectAttributes.addFlashAttribute("success", "Your ad was successfully submitted.");

        map.put("message", "Your ad was successfully submitted.");

        return map;
    }
}
