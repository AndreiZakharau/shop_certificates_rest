package com.epam.esm.repositorys.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.repositorys.EntityDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CertificateDao implements EntityDao<Certificate> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Certificate> getAll() {

        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Certificate",
                Certificate.class).getResultList();
    }

    @Override
    public void save(Certificate certificate) {

        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(certificate);

    }

    @Override
    public Certificate get(long id) {

        Session session = sessionFactory.getCurrentSession();
        return session.get(Certificate.class, id);
    }

    @Override
    public void delete(long id) {

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Certificate " +
                "where id =:certificateId ");
        query.setParameter("certificateId", id);
        query.executeUpdate();


    }
}
