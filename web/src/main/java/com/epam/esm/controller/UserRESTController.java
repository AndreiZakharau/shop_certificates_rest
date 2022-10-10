package com.epam.esm.controller;

import com.epam.esm.entity.User;
import com.epam.esm.mapper.impl.userMapper.UserModelReadMapper;
import com.epam.esm.Dto.orderDto.CreateOrder;
import com.epam.esm.Dto.userDto.CreateUser;
import com.epam.esm.Dto.userDto.ReadUser;
import com.epam.esm.pagination.Pagination;
import com.epam.esm.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1.1")
@RequiredArgsConstructor
public class UserRESTController {

    @Autowired
    private UserServiceImpl userService;

    private final UserModelReadMapper readMapper;

    /**
     * Created new userDto
     *
     * @param user the userDto
     * @return new userDto
     */
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user) {
        userService.saveEntity(user);
        allUsersLink(readMapper.mapFrom(user));
        return user;
    }

    /**
     * @param page the page
     * @param size the size
     * @return userModel
     */
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ReadUser> listAllUsers
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
        List<ReadUser> models = userService.getAllEntity(size, offset);
        return CollectionModel.of(models, prevLink, nextLink);
    }

    /**
     * Get userDto by id
     *
     * @param id the id
     * @return userDto
     */
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ReadUser> getUserById(@PathVariable long id) {
        Optional<ReadUser> userModel = Optional.ofNullable(userService.getEntity(id)).get();
        userModel.get().add(linkTo(methodOn(UserRESTController.class)
                .listAllUsers(1,5))
                .withRel("usrs")
                .withType(HttpMethod.GET.name()));
        return userModel;
    }

    /**
     * update userDto by id
     *
     * @param user the userDto
     * @param id   the id
     * @return the exposed userDto
     */
    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody User user, @PathVariable long id) {
        userService.updateEntity(id, user);
        return user;
    }

    /**
     * delete userDto by id
     *
     * @param id the id
     * @return string response
     */
    @DeleteMapping("users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteUser(@PathVariable long id) {
        userService.deleteEntity(id);
        return "User with ID = " + id + ", was deleted.";
    }

    /**
     * get userDto by name
     *
     * @param name the name
     * @return userDto
     */
    @GetMapping("/users/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<CreateUser> getUserByName(@PathVariable String name) {
        Optional<CreateUser> userModel =userService.getUserByName(name);
        allUsersLink(userModel.get());
        return userModel;
    }

    /**
     * @param userId        the userID
     * @param certificateId the CertificateId
     * @return OrderModel
     */
    @PostMapping("users/purchase")
    public CreateOrder purchaseCertificate(@RequestParam long userId, @RequestParam long certificateId) {
        return userService.purchaseCertificate(userId, certificateId);
    }

    private void allUsersLink(CreateUser userModel) {
        userModel.add(linkTo(methodOn(UserRESTController.class)
                .listAllUsers(1,5))
                .withRel("usrs")
                .withType(HttpMethod.GET.name()));
    }

}
