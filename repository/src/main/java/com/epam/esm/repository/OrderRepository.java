package com.epam.esm.repository;

import com.epam.esm.entity.Order;

public interface OrderRepository extends EntityRepository<Order> {

    void addEntity(Order order);

    void deleteEntity(long id);

    int countAllOrder();
}
