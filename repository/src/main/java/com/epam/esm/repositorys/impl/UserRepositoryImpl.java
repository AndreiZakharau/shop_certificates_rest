package com.epam.esm.repositorys.impl;

import com.epam.esm.entity.User;
import com.epam.esm.repositorys.UserRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private EntityManager manager;
//@Autowired
//private SessionFactory manager;
//
    @Override
    public Page<User> getAllEntity(Pageable pageable) {
        Session session = manager.unwrap(Session.class);
        return (Page<User>) session.createQuery("select u from User as u", User.class).getResultList();
    }

    @Override
    public Optional<User> getEntity(long id) {
        Session session = manager.unwrap(Session.class);
        return session.createQuery("select u from User as u where id =:id", User.class)
                .setParameter("id", id)
                .getResultList().stream().findFirst();
    }

    @Override
    public void addEntity(User user) {
        Session session = manager.unwrap(Session.class);
        session.saveOrUpdate(user);
    }

    @Override
    public void deleteEntity(long id) {
        Session session = manager.unwrap(Session.class);
        session.createQuery("delete from User where id =:id", User.class)
                .setParameter("id", id);
    }

    @Override
    public Optional<User> getUserByName(String name) {
        Session session = manager.unwrap(Session.class);
        return session.createQuery("select u from User as u where nickName =:name", User.class)
                .setParameter("name", name)
                .getResultList().stream().findFirst();
    }
}
