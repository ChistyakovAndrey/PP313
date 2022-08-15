package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.impl.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.impl.UserServiceImpl;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;

    @Autowired
    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("add")
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "admin/userInfo";
    }

    @GetMapping("/all_users")
    public String getAdminPage(Model model, Model model2, Principal principal) {
        model2.addAttribute("currentUser", userService.findByName(principal.getName()));
        List<User> userList = userService.getAllUsers();
        model.addAttribute("userList", userList);
        return "/admin/all_users";
    }

    @GetMapping("/user_info")
    public String updateUser(@RequestParam("id") Integer id, Model model) {
        User user = userService.findByID(id);
        model.addAttribute("user", user);
        return "admin/userInfo";
    }

    @GetMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam(value = "role")
    String admin) {
        boolean isAdmin = admin.equals("admin");
        userService.saveUser(user, isAdmin);
        return "redirect:/admin/all_users";
    }
    @GetMapping("/updateUser")
    public String update_User(@ModelAttribute("user") User user, @RequestParam(value = "roles1")
    String admin) {
        userService.updateUser(user, admin);
        return "redirect:/admin/all_users";
    }
    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin/all_users";
    }

}
