package com.epam.esm.service.impl;

import com.epam.esm.Dto.orderDto.CreateOrder;
import com.epam.esm.Dto.orderDto.OrderDto;
import com.epam.esm.Dto.orderDto.ReadOrder;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.mapper.impl.orderMapper.TransitionOrderFromCreateOrder;
import com.epam.esm.mapper.impl.orderMapper.TransitionOrderFromOrderDto;
import com.epam.esm.mapper.impl.orderMapper.TransitionReadOrderFromOrder;
import com.epam.esm.repository.impl.OrderRepositoryImpl;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepositoryImpl repository;
    private final TransitionReadOrderFromOrder readOrder;
    private final TransitionOrderFromCreateOrder orderFromCreateOrder;
    private final TransitionOrderFromOrderDto orderFromOrderDto;

    @Override
    public List<ReadOrder> getAllEntity(int limit, int offset) {
        List<Order>orders = repository.getAllEntity(limit, offset);
        return readOrder.buildReadOrderModel(orders);
    }

    @Override
    public void saveEntity(CreateOrder createOrder) {
        repository.addEntity(orderFromCreateOrder.mapFrom(createOrder));
    }

    @Override
    public void updateEntity(long id, OrderDto orderDto) {
        if(repository.getEntityById(id).isPresent()){
            repository.updateEntity(orderFromOrderDto.mapFrom(orderDto));
        } else {
            throw new NoSuchEntityException("Order from id " +id +"is empty");
        }
    }

    @Override
    public Optional<ReadOrder> findById(long id) {
        Optional<Order>order =repository.getEntityById(id);
        if(order.isEmpty())
            throw new NoSuchEntityException("Order from id " +id +" is empty.");
        return order.map(readOrder::mapFrom);
    }

    @Override
    public void deleteEntity(long id) {
        if(repository.getEntityById(id).isPresent()){
            repository.deleteEntity(id);
        }
        throw new NoSuchEntityException("Order from id " +id +" is empty.");
    }

    @Override
    public int countAll() {
        return repository.countAllOrder();
    }
}
