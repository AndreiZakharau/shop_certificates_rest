//package com.epam.esm.service.impl;
//
//import com.epam.esm.Dto.orderDto.CreateOrder;
//import com.epam.esm.Dto.orderDto.OrderDto;
//import com.epam.esm.Dto.orderDto.ReadOrder;
//import com.epam.esm.entity.Order;
//import com.epam.esm.exception.NoSuchEntityException;
//import com.epam.esm.mapper.impl.orderMapper.TransitionOrderFromCreateOrder;
//import com.epam.esm.mapper.impl.orderMapper.TransitionOrderFromOrderDto;
//import com.epam.esm.mapper.impl.orderMapper.TransitionReadOrderFromOrder;
//import com.epam.esm.repository.OrderRepository;
//import com.epam.esm.repository.impl.OrderRepositoryImpl;
//import com.epam.esm.service.OrderService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class OrderServiceImpl implements OrderService {
//
//    private final OrderRepository repository;
//    private final TransitionReadOrderFromOrder readOrder;
//    private final TransitionOrderFromCreateOrder orderFromCreateOrder;
//    private final TransitionOrderFromOrderDto orderFromOrderDto;
//
//    @Override
//    @Transactional
//    public List<ReadOrder> getAllEntity(int limit, int offset) {
//        List<Order>orders = repository.findAll(limit, offset);
//        return readOrder.buildReadOrderModel(orders);
//    }
//
//    @Override
//    @Transactional
//    public void saveEntity(CreateOrder createOrder) {
//        repository.save(orderFromCreateOrder.mapFrom(createOrder));
//    }
//
//    @Override
//    @Transactional
//    public void updateEntity(long id, OrderDto orderDto) {
//        if(repository.getReferenceById(id)!=null){
//            repository.save(orderFromOrderDto.mapFrom(orderDto));
//        } else {
//            throw new NoSuchEntityException("Order from id " +id +"is empty");
//        }
//    }
//
//    @Override
//    @Transactional
//    public ReadOrder findById(long userId) {
//        Order order =repository.getReferenceById(userId);
//        if(order==null)
//            throw new NoSuchEntityException("Order from  id " +userId +" is empty.");
//        return readOrder.mapFrom(order);
//    }
//
//    @Override
//    @Transactional
//    public void deleteEntity(long id) {
//        if(repository.getReferenceById(id)!=null){
//            repository.deleteById(id);
//        }
//        throw new NoSuchEntityException("Order from id " +id +" is empty.");
//    }
//
//    @Override
//    @Transactional
//    public Long countAll() {
//        return repository.count();
//    }
//
//    @Override
//    @Transactional
//    public List<ReadOrder> getOrdersByUserId(long userId, int limit, int offset){
//        List<Order>orders =repository.getOrdersByUserId(userId);
//        if(orders.isEmpty()){
//            throw new NoSuchEntityException("Order from user id " +userId +" is empty.");
//
//        }
//        return readOrder.buildReadOrderModel(orders);
//    }
//
//}
