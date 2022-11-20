package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Controller
@Slf4j
public class SignUpSignInController {

    private UserService userService;

    public SignUpSignInController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") User user, Model model, RedirectAttributes redirectAttributes) {
        log.debug("User: {}", user);
        boolean success = false;
        if (Objects.isNull(user)) {
            model.addAttribute("error", "Sign up failed");
        } else if (!userService.isUserNameAvailable(user.getUsername())) {
            model.addAttribute("error", "Username is not available");
        } else {
            if (userService.addUser(user)) {
                success = true;
            }
        }
        if (!success)
            return "/signup";
        redirectAttributes.addFlashAttribute("success", success);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String viewLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
}
