package com.mlx.web.controllers;

import com.mlx.domain.models.binding.UserRegisterBindingModel;
import com.mlx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/register")
    public String register() {
        return "user/register";
    }

    @PostMapping("/register")
    public String registerProccess(Model model, @Valid @ModelAttribute UserRegisterBindingModel bindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        if (!bindingModel.getPassword().equals(bindingModel.getConfirmPassword())) {
            model.addAttribute("username", bindingModel.getUsername());
            model.addAttribute("birthdate", bindingModel.getBirthdate());
            model.addAttribute("error", "Passwords do not match.");
            return "user/register";
        }
        if (errors.size() > 0) {
            model.addAttribute("username", bindingModel.getUsername());
            model.addAttribute("birthdate", bindingModel.getBirthdate());
            model.addAttribute("error", errors.get(errors.size() - 1));
            return "user/register";
        }
        if (this.userService.existsUserByUsername(bindingModel.getUsername())) {
            model.addAttribute("username", bindingModel.getUsername());
            model.addAttribute("birthdate", bindingModel.getBirthdate());
            model.addAttribute("error", "Username exists.");
            return "user/register";
        }
        this.userService.register(bindingModel);

        redirectAttributes.addFlashAttribute("success", "You are successfully registered.");

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/";
    }
}
