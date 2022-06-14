package com.epam.esm.controllers;

import com.epam.esm.entity.User;
import com.epam.esm.servises.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1.1")
public class UserRESTController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * Created new user
     * @param user the user
     * @return new user
     */
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user) {
        userService.saveEntity(user);
        return user;
    }

    /**
     * @return list users
     */
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public Page<User> listAllUsers(@PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable) {
        return userService.getAllEntity(pageable);
    }

    /**
     * Get user by id
     * @param id the id
     * @return user
     */
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<User> getUserById(@PathVariable long id) {
        return userService.getEntity(id);
    }

    /**
     * update user by id
     * @param user the user
     * @param id the id
     * @return the exposed user
     */
    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody User user, @PathVariable long id) {
        userService.updateEntity(id,user);
        return user;
    }

    /**
     * delete user by id
     * @param id the id
     * @return string response
     */
    @DeleteMapping("users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteUser(@PathVariable long id) {
        userService.deleteEntity(id);
        return "User with ID = " + id + ", was deleted.";
    }

    /**
     * get user by name
     * @param name the name
     * @return user
     */
    @GetMapping("/users/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<User> getUserByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }

}
