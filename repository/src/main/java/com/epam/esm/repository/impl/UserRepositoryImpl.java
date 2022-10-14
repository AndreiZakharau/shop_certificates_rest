package com.epam.esm.repository.impl;

import com.epam.esm.entity.User;
import com.epam.esm.entity.User_;
import com.epam.esm.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository, Serializable {

    @Autowired
    private SessionFactory manager;

    @Override
    public List<User> getAllEntity(int limit, int offset) {
        Session session = manager.getCurrentSession();
//        return session.createQuery("select u from User u", User.class)

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> user = cq.from(User.class);
        cq.select(user);
        return session.createQuery(cq)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public Optional<User> getEntityById(long id) {
        Session session = manager.getCurrentSession();
//        return session.createQuery("select u from User u where u.id =:id ", User.class)
//                .setParameter("id", id)
//                .getResultList().stream().findFirst();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> user = cq.from(User.class);
        cq.select(user).where(cb.equal(user.get(User_.ID),id));
        return session.createQuery(cq).uniqueResultOptional();
    }

    @Override
    public void addEntity(User user) {
        Session session = manager.getCurrentSession();
        session.saveOrUpdate(user);
    }

    @Override
    public void deleteEntity(long id) {
        Session session = manager.getCurrentSession();
        session.createQuery("delete from User where id =:id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void updateEntity(User user) {
        Session session = manager.getCurrentSession();
        session.merge(user);
    }

    @Override
    public Optional<User> getUserByName(String name) {
        Session session = manager.getCurrentSession();
//        return session.createQuery("select u from User as u where nickName =:name", User.class)
//                .setParameter("name", name)
//                .getResultList().stream().findFirst();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> user = cq.from(User.class);
        cq.select(user).where(cb.equal(user.get(User_.NICK_NAME),name));
        return session.createQuery(cq).uniqueResultOptional();
    }

    @Override
    public int countAllUsers() {
        Session session = manager.getCurrentSession();
//        return (int) session.createQuery("select u from User u",User.class).stream().count();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        return session.createQuery(criteriaQuery).uniqueResult().intValue();
    }

}
