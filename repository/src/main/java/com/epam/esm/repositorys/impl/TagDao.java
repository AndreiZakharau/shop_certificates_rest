package com.epam.esm.repositorys.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repositorys.EntityDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TagDao implements EntityDao<Tag> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Tag> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Tag",
                Tag.class).getResultList();
    }

    @Override
    public void save(Tag tag) {

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(tag);
    }

    @Override
    public Tag get(long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Tag.class,id);
    }

    @Override
    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();
        Query tagQuery = session.createQuery("delete from Tag " +
                "where id =:tagId");
        tagQuery.setParameter("tagId", id);
        tagQuery.executeUpdate();
    }

}
