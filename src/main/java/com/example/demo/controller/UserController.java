package com.example.demo.controller;



import com.example.demo.model.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping(value = "/save")
    public ResponseEntity<Void> saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<User> getUser( @PathVariable("id") Long id) {

        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // find all users
    @GetMapping(value = "/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value ="/generateRandomUser")
    public ResponseEntity<Void> generateRandomUser() {
         userService.generateRandomUser();
        return new ResponseEntity<>( HttpStatus.OK);
    }
    @GetMapping(value ="/getByName/{name}")
    public ResponseEntity<User> getByName(@PathVariable("name") String name) {
        User user = userService.findUserByName(name);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}