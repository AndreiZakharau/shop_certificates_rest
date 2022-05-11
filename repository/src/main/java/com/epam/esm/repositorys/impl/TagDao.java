//package com.epam.esm.repositorys.impl;
//
//import com.epam.esm.entity.Tag;
//import com.epam.esm.repositorys.EntityDao;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//public class TagDao implements EntityDao<Tag> {
//
//    private final SessionFactory sessionFactory;
//
//    @Autowired
//    public TagDao(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
//    @Override
//    public List<Tag> getAll() {
//        Session session = sessionFactory.getCurrentSession();
//        return session.createQuery("from Tag",
//                Tag.class).getResultList();
//    }
//
//    @Override
//    public void save(Tag tag) {
//
//        Session session = sessionFactory.getCurrentSession();
//        session.evict(tag);
//    }
//
//    @Override
//    public Tag get(long id) {
//        Session session = sessionFactory.getCurrentSession();
//        return session.get(Tag.class,id);
//    }
//
//    @Override
//    public void delete(long id) {
//        Session session = sessionFactory.getCurrentSession();
//        session.detach(session.get(Tag.class,id));
////        Query tagQuery = session.createQuery("delete from Tag " +
////                "where id =:tagId");
////        tagQuery.setParameter("tagId", id);
////        tagQuery.executeUpdate();
//    }
//
//}
