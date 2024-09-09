package com.sergeev.rogii.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sergeev.rogii.model.User;
import com.sergeev.rogii.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService service) {
        this.userService = service;
    }

    // GET /api/users?count=**
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(@RequestParam int count /*Может возникнуть ошибка если придёт лонг, дабл и т.д.(если нужно можно предусмотреть)*/) {
        List<User> userList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            userList.add(userService.generateUser(i));
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    // GET /api/user/{id}
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id/*Может возникнуть ошибка если придёт лонг, дабл и т.д.(если нужно можно предусмотреть)*/) {
        User user = userService.generateUserWithAddress(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // POST /api/user
    @PostMapping(value = "/user", consumes = "application/json;charset=utf-8", produces = "application/json;charset=utf-8")
    public ResponseEntity<User> createUser(@RequestBody String body) { // Можно создать модельку с полями как в json и запарсить в неё.
        JsonObject jsonObject = JsonParser.parseString(body).getAsJsonObject();
        User user = new User();
        user.setId(userService.generateRandomId());
        user.setFirstName(jsonObject.get("first_name").getAsString());
        user.setLastName(jsonObject.get("last_name").getAsString());
        user.setEmail(jsonObject.get("email").getAsString());
        user.setAddress(jsonObject.get("address").getAsString());
        user.setCreatedAt();
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
