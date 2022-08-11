package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.impl.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.impl.UserServiceImpl;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserServiceImpl userService;
    private RoleServiceImpl roleService;

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
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        roleAdmin.setId(1);
        roleUser.setId(2);
        if(user.getRoles() == null){
            Set<Role> roles = new HashSet<>();
            roles.add(roleUser);
            if(isAdmin){
                roles.add(roleAdmin);
            }
            user.setRoles(roles);
        }
        userService.saveUser(user);
        return "redirect:/admin/all_users";
    }
    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin/all_users";
    }

}
