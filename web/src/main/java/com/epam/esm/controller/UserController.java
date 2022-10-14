package com.epam.esm.controller;

import com.epam.esm.Dto.orderDto.CreateOrder;
import com.epam.esm.Dto.userDto.CreateUser;
import com.epam.esm.Dto.userDto.ReadUser;
import com.epam.esm.Dto.userDto.UserDto;
import com.epam.esm.pagination.Pagination;
import com.epam.esm.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1.1/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * Created new user
     *
     * @param user the CreateUser (user Dto)
     * @return new CreateUser (user Dto)
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUser addUser(@RequestBody CreateUser user) {
        userService.saveEntity(user);
        return user;
    }

    /**
     * @param page the page
     * @param size the size
     * @return readUser (user Dto)
     */
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ReadUser> listAllUsers(@RequestParam("page") int page,
                                                  @RequestParam("size") int size) {
        int offset = Pagination.offset(page, size);
        int totalRecords = userService.countAll();
        int pages = Pagination.findPages(totalRecords, size);
        int lastPage = Pagination.findLastPage(pages, size, totalRecords);
        Link prevLink = linkTo(methodOn(UserController.class).listAllUsers(Pagination.findPrevPage(page), size))
                .withRel("prev");
        Link nextLink = linkTo(methodOn(UserController.class).listAllUsers(Pagination.findNextPage(page, lastPage), size))
                .withRel("next");
        List<ReadUser> models = userService.getAllEntity(size, offset);
        return CollectionModel.of(models, prevLink, nextLink);
    }

    /**
     * Get user by id
     *
     * @param id the id
     * @return readUser (user Dto)
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ReadUser> getUserById(@PathVariable long id) {
        Optional<ReadUser> userModel = Optional.ofNullable(userService.findById(id)).get();
        allUsersLink(userModel.get());
        return userModel;
    }

    /**
     * update userDto by id
     *
     * @param userDto the user Dto
     * @param id   the id
     * @return the exposed readUser (user Dto)
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@RequestBody UserDto userDto, @PathVariable long id) {
        userService.updateEntity(id, userDto);
        return userDto;
    }

    /**
     * delete userDto by id
     *
     * @param id the id
     * @return string response
     */
    @DeleteMapping("/{id}")
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
    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ReadUser> getUserByName(@PathVariable String name) {
        Optional<ReadUser> userModel =userService.getUserByName(name);
        allUsersLink(userModel.get());
        return userModel;
    }

    /**
     * @param userId        the userID
     * @param certificateId the CertificateId
     * @return OrderModel
     */
    @PostMapping("/purchase") //todo
    public CreateOrder purchaseCertificate(@RequestParam long userId, @RequestParam long certificateId) {
        return userService.purchaseCertificate(userId, certificateId);
    }

    private void allUsersLink(ReadUser userModel) {
        userModel.add(linkTo(methodOn(UserController.class)
                .listAllUsers(1,5))
                .withRel("usrs")
                .withType(HttpMethod.GET.name()));
    }

}
