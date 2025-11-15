package com.microservice.user.service;

import com.microservice.user.exception.EmailAlreadyExistException;
import com.microservice.user.exception.UserNotFoundException;
import com.microservice.user.model.User;
import com.microservice.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public ResponseEntity<?> addUser(User user)
    {

        boolean isPresent =  userRepo.existsByEmail(user.getEmail());
        if (isPresent)
        {
            throw new EmailAlreadyExistException("Email already Exist!!");
        }


        try
        {
            User savedUser = userRepo.save(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> fetchUserById(int id) {
        System.out.println("id"+id);
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent())
        {
            User user = optionalUser.get();
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
        throw  new UserNotFoundException("User not found with provided id");

    }
}
