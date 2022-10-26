package zakharau.com.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import zakharau.com.entity.Order;

@Repository
public class SaveOrder {

    @Autowired
    private SessionFactory manager;


    public Order saveOrder(Order order) {
        Session session = manager.getCurrentSession();
        session.saveOrUpdate(order);
        return order;
    }


}
