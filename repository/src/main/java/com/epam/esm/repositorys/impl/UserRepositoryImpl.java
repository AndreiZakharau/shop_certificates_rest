package com.epam.esm.repositorys.impl;

import com.epam.esm.entitys.User;
import com.epam.esm.repositorys.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        return session.createQuery("select u from User  u", User.class).getResultList();
    }

    @Override
    public Optional<User> getEntity(long id) {
        Session session = manager.getCurrentSession();
        return session.createQuery("select u from User u where u.id =:id", User.class)
                .setParameter("id", id)
                .getResultList().stream().findFirst();
    }

    @Override
    public void addEntity(User user) {
        Session session = manager.getCurrentSession();
        session.saveOrUpdate(user);
    }

    @Override
    public void deleteEntity(long id) {
        Session session = manager.getCurrentSession();
        session.createQuery("delete from User where id =:id", User.class)
                .setParameter("id", id);
    }

    @Override
    public void updateEntity(User user) {
        Session session = manager.getCurrentSession();
        session.update(user);
    }

    @Override
    public Optional<User> getUserByName(String name) {
        Session session = manager.getCurrentSession();
        return session.createQuery("select u from User as u where nickName =:name", User.class)
                .setParameter("name", name)
                .getResultList().stream().findFirst();
    }

}
