package com.epam.esm.controllers;

import com.epam.esm.entitys.User;
import com.epam.esm.models.orders.CreateOrderModel;
import com.epam.esm.models.users.ReadUserModel;
import com.epam.esm.models.users.UserModel;
import com.epam.esm.pagination.Pagination;
import com.epam.esm.servises.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1.1")
public class UserRESTController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * Created new user
     *
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
     * @param page the page
     * @param size the size
     * @return userModel
     */
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<UserModel> listAllUsers
    (@RequestParam("page") int page,
     @RequestParam("size") int size) {
        int offset = Pagination.offset(page, size);
        int totalRecords = userService.countAllUsers();
        int pages = Pagination.findPages(totalRecords, size);
        int lastPage = Pagination.findLastPage(pages, size, totalRecords);
        Link prevLink = linkTo(methodOn(UserRESTController.class).listAllUsers(Pagination.findPrevPage(page), size))
                .withRel("prev");
        Link nextLink = linkTo(methodOn(UserRESTController.class).listAllUsers(Pagination.findNextPage(page, lastPage), size))
                .withRel("next");
        List<UserModel> models = userService.getAllEntity(size, offset);
        return CollectionModel.of(models, prevLink, nextLink);
    }

    /**
     * Get user by id
     *
     * @param id the id
     * @return user
     */
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<UserModel> getUserById(@PathVariable long id) {
        return userService.getEntity(id);
    }

    /**
     * update user by id
     *
     * @param user the user
     * @param id   the id
     * @return the exposed user
     */
    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody User user, @PathVariable long id) {
        userService.updateEntity(id, user);
        return user;
    }

    /**
     * delete user by id
     *
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
     *
     * @param name the name
     * @return user
     */
    @GetMapping("/users/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ReadUserModel> getUserByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }

    /**
     * @param userId        the userID
     * @param certificateId the CertificateId
     * @return OrderModel
     */
    @PostMapping("users/purchase")
    public CreateOrderModel purchaseCertificate(@RequestParam long userId, @RequestParam long certificateId) {
        return userService.purchaseCertificate(userId, certificateId);
    }

}
