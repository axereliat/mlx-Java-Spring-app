package com.mlx.web.controllers;

import com.mlx.domain.models.binding.CategoryCreateBindingModel;
import com.mlx.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CategoryService categoryService;

    @Autowired
    public AdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categories", this.categoryService.findAll());

        return "admin/categories/list";
    }

    @GetMapping("/categories/create")
    public String create() {
        return "admin/categories/create";
    }

    @PostMapping("/categories/create")
    public String createProcess(Model model, @Valid @ModelAttribute CategoryCreateBindingModel bindingModel, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        List<String> errors = bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        if (errors.size() > 0) {
            redirectAttributes.addFlashAttribute("error", errors.get(errors.size() - 1));
            return "redirect:/admin/categories/create";
        }

        this.categoryService.create(bindingModel);

        redirectAttributes.addFlashAttribute("success", "Category is successfully created!");

        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String edit(Model model, @PathVariable Integer id) {
        model.addAttribute("category", this.categoryService.findById(id));

        return "admin/categories/edit";
    }

    @PostMapping("/categories/edit/{id}")
    public String editProcess(@Valid @ModelAttribute CategoryCreateBindingModel bindingModel,
                              @PathVariable Integer id,
                              RedirectAttributes redirectAttributes,
                              BindingResult bindingResult) {
        List<String> errors = bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        if (errors.size() > 0) {
            redirectAttributes.addFlashAttribute("error", errors.get(errors.size() - 1));
            return "redirect:/admin/categories/edit/" + id;
        }
        this.categoryService.edit(bindingModel, id);
        redirectAttributes.addFlashAttribute("success", "Category was successfully edited");
        return "redirect:/admin/categories";
    }
}
