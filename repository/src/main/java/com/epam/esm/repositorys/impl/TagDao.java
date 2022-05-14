//package com.epam.esm.repositorys.impl;
//
//import com.epam.esm.entity.Tag;
//import com.epam.esm.repositorys.EntityDao;
//import org.hibernate.Session;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//
//@Repository
//public class TagDao implements EntityDao<Tag> {
//
//    private final EntityManager manager;
//
//    @Autowired
//    public TagDao(EntityManager manager) {
//        this.manager = manager;
//    }
//
//    @Override
//    public List<Tag> getAllEntity() {
//        Session session = manager.unwrap(Session.class);
//        return session.createQuery("from Tag",
//                Tag.class).getResultList();
//    }
//
//    @Override
//    public void addEntity(Tag tag) {
//
//        Session session = manager.unwrap(Session.class);
//        session.evict(tag);
//    }
//
//    @Override
//    public Tag getEntity(long id) {
//        Session session = manager.unwrap(Session.class);
//        return session.get(Tag.class,id);
//    }
//
//    @Override
//    public void deleteEntity(long id) {
//        Session session = manager.unwrap(Session.class);
//        session.detach(session.get(Tag.class,id));
//
//    }
//
//    @Override
//    public void updateEntity(long id, Tag tag) {
//        Session session = manager.unwrap(Session.class);
//
//
//    }
//
//
//
//}
