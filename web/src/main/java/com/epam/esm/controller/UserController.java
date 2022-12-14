package com.epam.esm.controller;

import com.epam.esm.Dto.orderDto.CreateOrder;
import com.epam.esm.Dto.orderDto.ReadOrder;
import com.epam.esm.Dto.userDto.CreateUser;
import com.epam.esm.Dto.userDto.ReadUser;
import com.epam.esm.Dto.userDto.UserDto;
import com.epam.esm.link.linkImpl.AddOrderLink;
import com.epam.esm.link.linkImpl.AddUserLink;
import com.epam.esm.pagination.Pagination;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1.1/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    private final OrderServiceImpl orderService;
    private final AddUserLink userLink;
    private final AddOrderLink orderLink;

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
        ReadUser readUser = new ReadUser();
        List<ReadUser> models = userService.getAllEntity(size, offset);
        userLink.pageLink(page,size,readUser);
        return CollectionModel.of(models.stream().peek(userLink::addLinks)
                .collect(Collectors.toList()),
                readUser.getLinks());
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
        userLink.addLinks(userModel.get());
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
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long id) {
        userService.deleteEntity(id);
    }

    /**
     * get readUser by name
     *
     * @param name the name
     * @return readUser
     */
    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ReadUser> getUserByName(@PathVariable String name) {
        Optional<ReadUser> userModel =userService.getUserByName(name);
        userLink.addLinks(userModel.get());
        return userModel;
    }

    @GetMapping("/orders")
    public CollectionModel<ReadOrder> getOrders(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                @RequestParam(value = "size",  defaultValue = "1", required = false) int size){
        int offset = Pagination.offset(page, size);
        ReadOrder readOrder = new ReadOrder();
        List<ReadOrder> models = orderService.getAllEntity(size, offset);
        orderLink.pageLink(page,size,readOrder);
        return CollectionModel.of(models.stream().peek(orderLink::addLinks).collect(Collectors.toList()), readOrder.getLinks());
    }

    /**
     * @param userId        the userID
     * @param certificateId the CertificateId
     * @return OrderModel
     */
    @PostMapping("/orders")
    public CreateOrder purchaseCertificate(@RequestParam long userId, @RequestParam long certificateId) {
        return userService.purchaseCertificate(userId, certificateId);
    }

    /**
     * Get order by user id
     *
     * @param id the user id
     * @return readOrder (order Dto)
     */
    @GetMapping("{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ReadOrder> getOrderByUserId(@PathVariable long id,
                                            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                            @RequestParam(value = "size",  defaultValue = "10", required = false) int size) {
        int offset = Pagination.offset(page, size);
        ReadOrder readOrder = new ReadOrder();
        List<ReadOrder> orders = (orderService.getOrdersByUserId(id,size, offset));
        orderLink.pageLink(page,size,readOrder);
        return CollectionModel.of(orders.stream().peek(orderLink::addLinks).collect(Collectors.toList()),readOrder.getLinks());
    }

    /**
     * delete orderDto by id
     *
     * @param id the id
     * @return string response
     */
    @DeleteMapping("{}/orders/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable long id) {
        orderService.deleteEntity(id);
    }


}
