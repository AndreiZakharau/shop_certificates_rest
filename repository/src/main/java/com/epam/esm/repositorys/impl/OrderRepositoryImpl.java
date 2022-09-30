package com.epam.esm.repositorys.impl;

import com.epam.esm.entitys.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl {

    @Autowired
    private SessionFactory manager;

    public Order saveOrder(Order order) {
        Session session = manager.getCurrentSession();
        session.saveOrUpdate(order);
        return order;
    }


}
