package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.impl.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.impl.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String userProfile(@RequestParam(value = "id", required = false) Integer id, Model model,
                              Model model2, Principal principal) {
        User currentUser = userService.findByName(principal.getName());
        if (currentUser.isAdmin()) {
            if(id == null){
                User user = userService.findByName(principal.getName());
                model.addAttribute("user", user);
                model2.addAttribute("currentUser", user);
                return "/user/profile";
            }
            User user = userService.findByID(id);
            model.addAttribute("user", user);
            model2.addAttribute("currentUser", user);
            return "/user/profile";
        } else {
            model.addAttribute("user", currentUser);
            model2.addAttribute("currentUser", currentUser);
            return "/user/profile";
        }
    }
}
