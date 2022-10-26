//package com.epam.esm.link.linkImpl;
//
//import com.epam.esm.Dto.userDto.ReadUser;
//import com.epam.esm.controller.UserController;
//import com.epam.esm.link.AddAbstractLink;
//import com.epam.esm.pagination.Pagination;
//import com.epam.esm.service.impl.UserServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//
//@Component
//@RequiredArgsConstructor
//public class AddUserLink extends AddAbstractLink<ReadUser> {
//
//    private final UserServiceImpl service;
//
//    @Override
//    public void addLinks(ReadUser readUser) {
//        long id = readUser.getId();
//        addIdLink(UserController.class, readUser, id, SELF_LINK);
//        readUser.add(linkTo(UserController.class)
//                .slash(id)
//                .slash("orders")
//                .withRel("orders"));
//
//    }
//
//    @Override
//    public void pageLink(int page, int size, ReadUser readUser) {
//        int totalRecords = service.countAll();
//        int pages = Pagination.findPages(totalRecords, size);
//        int lastPage = Pagination.findLastPage(pages, size, totalRecords);
//        readUser.add(linkTo(methodOn(UserController.class).listAllUsers(Pagination.findPrevPage(page), size))
//                .withRel("prev"));
//        readUser.add(linkTo(methodOn(UserController.class).listAllUsers(Pagination.findNextPage(page, lastPage), size))
//                .withRel("next"));
//    }
//
//}
