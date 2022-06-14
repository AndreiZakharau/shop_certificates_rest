package com.epam.esm.repositorys.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repositorys.TagRepository;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class TagRepositoryImpl implements TagRepository {

    @Autowired
    private EntityManager manager;
//    @Autowired
//    private SessionFactory manager;

    @Override
    public Page<Tag> getAllEntity(Pageable pageable) {
        Session session = manager.unwrap(Session.class);
        Query<Tag> query = session.createQuery("from Tag ", Tag.class);
        return (Page<Tag>) query.getResultList();
    }

    @Override
    public void addEntity(Tag tag) {
        Session session = manager.unwrap(Session.class);
        session.saveOrUpdate(tag);
    }

    @Override
    public Optional<Tag> getEntity(long id) {
        Session session = manager.unwrap(Session.class);
        return session
                .createQuery("select t from com.epam.esm.entity.Tag as t where id = :id", Tag.class)
                .setParameter("id", id).getResultList().stream().findFirst();
    }

    @Override
    public void deleteEntity(long id) {
        Session session = manager.unwrap(Session.class);
        Query<Tag> query = session.createQuery("delete from Tag where id =:id");
        query.setParameter("id",id);
        query.executeUpdate();
    }

//    public void updateEntity(long id, Tag tag) {
//        Session session = manager.unwrap(Session.class);
//        session.update(tag);
//    }

}
