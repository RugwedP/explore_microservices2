package com.microservice.user.controller;

import com.microservice.user.model.User;
import com.microservice.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {


    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user)
    {

        if(user.getPassword() != null)
        {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return this.userService.addUser(user);
    }

    @GetMapping("fetch/{id}")
    public ResponseEntity<?> fetchUserById(@PathVariable int id)
    {
        return userService.fetchUserById(id);
    }
}
