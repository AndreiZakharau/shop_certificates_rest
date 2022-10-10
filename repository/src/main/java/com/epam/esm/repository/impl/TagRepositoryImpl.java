package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        return session.createQuery("select t from Tag t", Tag.class)
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
    public Optional<Tag> getEntityById(long id) {
        Session session = manager.getCurrentSession();
        return session
                .createQuery("select t from Tag t where t.id = :id", Tag.class)
                .setParameter("id", id).uniqueResultOptional();
    }

    @Override
    public void deleteEntity(long id) {
        Session session = manager.getCurrentSession();
        session.createQuery("delete from Tag where id =:id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void updateEntity(Tag tag) {
        Session session = manager.getCurrentSession();
        session.merge(tag);

    }

    @Override
    public List<Tag> getTags() {
        Session session = manager.getCurrentSession();
        return session.createQuery("select t from Tag  t", Tag.class).getResultList();
    }

    public int countAllTags() {
        Session session = manager.getCurrentSession();
        return (int) session.createQuery("select t from Tag t",Tag.class).stream().count();

    }

    public Optional<Tag> getTagByName(String tagName) {
        Session session = manager.getCurrentSession();
        return session.createQuery("select t from Tag  t where tagName =:tagName", Tag.class)
                .setParameter("tagName", tagName).uniqueResultOptional();
    }

    public Tag getPopularTagWithUser() {
        Session session = manager.getCurrentSession();
        return session.createNativeQuery(
                "select tags.id,tags.tag_name from tags " +
                        "join (select t.tag_name as tag, count(t.tag_name) as count_tag from tags as t " +
                        "join certificates_tag as c_t on c_t.tag_id = t.id " +
                        "join gift_certificate as c on c.id = c_t.certificate_id " +
                        "join orders_certificates as o_c on o_c.certificate_id = c.id " +
                        "join orders as o on o.id = o_c.order_id join users as u on u.id = o.user_id " +
                        "join (select man.id as u_id, man.nick_name, max(users_sum.sum_orders) from users as man " +
                        "join orders as checks on checks.user_id = man.id " +
                        "join (select us.id, sum(ord.cost) as sum_orders from orders as ord " +
                        "join users as us on us.id = ord.user_id group by ord.user_id) as users_sum " +
                        "where users_sum.id = man.id) as resault on resault.u_id = o.user_id " +
                        "group by t.tag_name) as count_tags on count_tags.tag = tags.tag_name " +
                        "having max(count_tags.count_tag)", Tag.class).uniqueResult();
    }

}
