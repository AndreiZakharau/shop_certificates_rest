package com.epam.esm.repositorys.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repositorys.CertificateRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

    @Autowired
    private EntityManager manager;

    @Override
    public List<Certificate> getAllEntity() {
        Session session = manager.unwrap(Session.class);
        return session.createQuery("select c from Certificate c",
                Certificate.class).getResultList();
    }

    @Override
    public Optional<Certificate> getEntity(long id) {
        Session session = manager.unwrap(Session.class);
        return session
                .createQuery("select c from Certificate as c where c.id = :id ", Certificate.class)
                .setParameter("id", id).getResultList().stream().findFirst();
    }

    @Override
    public void addEntity(Certificate certificate) {
        Session session = manager.unwrap(Session.class);
        session.saveOrUpdate(certificate);
    }

    @Override
    public void deleteEntity(long id) {
      Session session = manager.unwrap(Session.class);
        Query query = session.createQuery("delete from Certificate  where id =:id");
        query.setParameter("id",id);
        query.executeUpdate();
    }

    public List<Object[]> getAllCertificatesAndTags(){
        Session session = manager.unwrap(Session.class);
        return session.createNativeQuery("select tags.id, tags.tag_name, c.id, c.certificate_name, c.description,c.duration, c.price, c.create_date, c.last_update_date" +
                " FROM gift_certificate AS c JOIN certificates_tag AS ct ON ct.certificate_id = c.id " +
                "JOIN tags ON tags.id = ct.tag_id ORDER BY tags.name", Object[].class).getResultList();
    }

    public Object saveCertificatesTag(Certificate c, Tag t){
        Session session = manager.unwrap(Session.class);
        return session.createNativeQuery("INSERT INTO certificates_tag VALUES( c.id =:c , t.id =:t)")
                .setParameter("c", c.getId())
                .setParameter("t", t.getId());
    }

    public List<Object[]> getCertificateAndTag(Certificate c, Tag t){
        Session session = manager.unwrap(Session.class);
        return session.createNativeQuery("SELECT * FROM certificates_tag" +
                " WHERE certificate_id=:cId and tag_id=:tId",Object[].class)
                .setParameter("cId", c.getId())
                .setParameter("tId", t.getId())
                .getResultList();
    }

    public List<Certificate> getCertificatesByTag(Tag t){
        Session session = manager.unwrap(Session.class);
        return session.createQuery("select c from Certificate c where Tag.tagName =:name", Certificate.class)
                .setParameter("name", t.getTagName())
                .getResultList();
    }

    public List getCertificatesByName(String name) {
        Session session = manager.unwrap(Session.class);
        return session.createNativeQuery("SELECT * FROM gift_certificate as c WHERE  RLIKE (c.certificate_name =:name)")
                .setParameter("name", name)
                .getResultList();
    }
}


