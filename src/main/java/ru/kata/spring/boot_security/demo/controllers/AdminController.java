package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.impl.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.impl.UserServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("add")
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "admin/userInfo";
    }

    @GetMapping("/all_users")
    public String getAdminPage(Model model) {
        List<User> userList = userService.getAllUsers();
        for(User u : userList){
            System.out.println(u);
        }
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
    public String saveUser(@ModelAttribute("user") User user, @RequestParam(value = "admin", required = false)
    boolean isAdmin) {
        userService.saveUser(user, isAdmin);
        return "redirect:/admin/all_users";
    }
    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin/all_users";
    }

}
