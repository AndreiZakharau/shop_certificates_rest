package com.epam.esm.repositorys.impl;

import com.epam.esm.entitys.Tag;
import com.epam.esm.repositorys.TagRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@Repository
public class TagRepositoryImpl implements TagRepository, Serializable {


    @Autowired
    private SessionFactory manager;

    @Override
    public List<Tag> getAllEntity(int limit, int offset) {
        Session session = manager.getCurrentSession();
//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<Tag> criteria = cb.createQuery(Tag.class);
//        Root<Tag> tagRoot = criteria.from(Tag.class);
//        criteria.select(tagRoot);
//        Query<Tag>query = session.createQuery(criteria);
//        query.setMaxResults(limit);
//        query.setFirstResult(offset);
//        return query.getResultList();
        return session.createQuery("select t from Tag t order by t.id", Tag.class)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();

    }

    @Override
    public void addEntity(Tag tag) {
        Session session = manager.getCurrentSession();
        session.saveOrUpdate(tag);
    }

    @Override
    public Optional<Tag> getEntity(long id) {
        Session session = manager.getCurrentSession();
        return session
                .createQuery("select t from Tag t where t.id = :id", Tag.class)
                .setParameter("id", id).uniqueResultOptional();
    }

    @Override
    public void deleteEntity(long id) {
        Session session = manager.getCurrentSession();
        Query query = session.createQuery("delete from Tag where id =:id", Tag.class);
        query.setParameter("id",id);
        query.executeUpdate();
    }

    @Override
    public void updateEntity(Tag tag) {
        Session session = manager.getCurrentSession();
        session.merge(tag);

    }

    @Override
    public List<Tag> getOnlyTags() {
        Session session = manager.getCurrentSession();
        return session.createQuery("select t from Tag  t order by t.id",Tag.class).getResultList();
    }

    public int countAllTags() {
        Session session = manager.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Tag> tagRoot = criteriaQuery.from(Tag.class);
        criteriaQuery.select(criteriaBuilder.count(tagRoot));
        return session.createQuery(criteriaQuery).uniqueResult().intValue();

    }


}
