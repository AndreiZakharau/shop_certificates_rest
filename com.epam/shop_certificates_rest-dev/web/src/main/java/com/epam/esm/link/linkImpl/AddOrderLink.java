//package com.epam.esm.link.linkImpl;
//
//import com.epam.esm.Dto.orderDto.ReadOrder;
//import com.epam.esm.controller.UserController;
//import com.epam.esm.link.AddAbstractLink;
//import com.epam.esm.pagination.Pagination;
//import com.epam.esm.service.impl.OrderServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//
//@Component
//@RequiredArgsConstructor
//public class AddOrderLink extends AddAbstractLink<ReadOrder> {
//
//    private final OrderServiceImpl service;
//
//    @Override
//    public void addLinks(ReadOrder readOrder) {
//        long id = readOrder.getId();
//        addIdLink(UserController.class,readOrder,id,SELF_LINK);
//    }
//
//    @Override
//    public void pageLink(int page, int size, ReadOrder readOrder) {
//        int totalRecords = service.countAll();
//        int pages = Pagination.findPages(totalRecords, size);
//        int lastPage = Pagination.findLastPage(pages, size, totalRecords);
//        readOrder.add(linkTo(methodOn(UserController.class).getOrders(Pagination.findPrevPage(page), size))
//                .withRel("prev"));
//        readOrder.add(linkTo(methodOn(UserController.class).getOrders(Pagination.findNextPage(page, lastPage), size))
//                .withRel("next"));
//    }
//}
