package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void saveUser(User user, boolean isAdmin);
    void deleteUser(Integer id);
    void updateUser(User user, String isAdmin);
    void updateUser(User user);
    User findByName(String name);
    User findByEmail(String mail);
    List<User> getAllUsers();
    User findByID(Integer id);
}
