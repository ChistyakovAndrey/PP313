package ru.kata.spring.boot_security.demo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public UserServiceImpl() {
    }

    public User findByID(Integer id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    @Override
    public void saveUser(User user, boolean isAdmin) {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        roleAdmin.setId(1);
        roleUser.setId(2);
        Set<Role> roles = new HashSet<>();
//        if(user.getRoles() == null){
//            Set<Role> roles = new HashSet<>();
//            roles.add(roleUser);
//            if(isAdmin){
//                roles.add(roleAdmin);
//            }
//            user.setRoles(roles);
//        }
        if(isAdmin){
            roles.add(roleAdmin);
        }
        else{
            roles.add(roleUser);
        }
        user.setRoles(roles);
        //if(user.getPassword().length() < 20){
            user.setPassword(encoder.encode(user.getPassword()));
        //}
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void updateUser(User user, String admin) {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        roleAdmin.setId(1);
        roleUser.setId(2);
        boolean isAdmin = Objects.equals(admin, "admin");
        if(user.getPassword().isEmpty()){
            user.setPassword(findByID(user.getId()).getPassword());
        }
        else{
            user.setPassword(encoder.encode(user.getPassword()));
        }
        Set<Role> roles = new HashSet<>();
        if(isAdmin){
            roles.add(roleAdmin);
        }
        else{
            roles.add(roleUser);
        }
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }


    @Transactional
    @Override
    public void updateUser(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User findByEmail(String mail) {
        return userRepository.findByEmail(mail);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username);
    }
}
