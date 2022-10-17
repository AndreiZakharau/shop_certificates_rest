package com.epam.esm.repository.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.Order_;
import com.epam.esm.entity.User;
import com.epam.esm.entity.User_;
import com.epam.esm.repository.OrderRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private SessionFactory manager;

    @Override
    public void addEntity(Order order) {
        Session session = manager.getCurrentSession();
        session.saveOrUpdate(order);
    }


    @Override
    public List<Order> getAllEntity(int limit, int offset) {
        Session session = manager.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> order = cq.from(Order.class);
        cq.select(order);
        return session.createQuery(cq)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public Optional<Order> getEntityById(long id) {
        Session session = manager.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> order = cq.from(Order.class);
        Join<Order, User> user = order.join(Order_.USER);
        cq.select(order).where(cb.equal(user.get(User_.ID), id));
        return session.createQuery(cq).uniqueResultOptional();
    }


    @Override
    public void updateEntity(Order order) {
        Session session = manager.getCurrentSession();
        session.saveOrUpdate(order);

    }

    @Override
    public void deleteEntity(long id) {
        Session session = manager.getCurrentSession();
        session.createQuery("delete from Order where id =:id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public int countAllOrder() {
        Session session = manager.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        return session.createQuery(criteriaQuery).uniqueResult().intValue();
    }

    public List<Order> getOrdersByUserId(long id, int limit, int offset) {
        Session session = manager.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> order = cq.from(Order.class);
        Join<Order, User> user = order.join(Order_.USER);
        cq.select(order).where(cb.equal(user.get(User_.ID), id));
        return session.createQuery(cq)
                .setMaxResults(limit)
                .setFirstResult(offset).getResultList();
    }

}
