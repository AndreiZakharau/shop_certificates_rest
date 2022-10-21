package com.epam.esm.service;

import com.epam.esm.Dto.orderDto.CreateOrder;
import com.epam.esm.Dto.orderDto.OrderDto;
import com.epam.esm.Dto.orderDto.ReadOrder;

import java.util.List;

public interface OrderService extends EntityService<ReadOrder, CreateOrder, OrderDto>{

    List<ReadOrder> getOrdersByUserId(long id, int limit, int offset);
}
