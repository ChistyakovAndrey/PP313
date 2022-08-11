package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void saveUser(User user);
    void deleteUser(Integer id);
    void updateUser(User user);
    User findByName(String name);
    List<User> getAllUsers();
}
