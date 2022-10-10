package com.epam.esm.repository.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.CertificateRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

    @Autowired
    private SessionFactory manager;

    @Override
    public List<Certificate> getAllEntity(int limit, int offset) {
        Session session = manager.getCurrentSession();
        return session.createQuery("select c from Certificate c", Certificate.class)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public Optional<Certificate> getEntityById(long id) {
        Session session = manager.getCurrentSession();
        return session
                .createQuery("select c from Certificate c where c.id = :id order by c.id ", Certificate.class)
                .setParameter("id", id).uniqueResultOptional();
    }

    @Override
    public void addEntity(Certificate certificate) {
        Session session = manager.getCurrentSession();
        session.save(certificate);
    }

    public void deleteEntity(Certificate certificate) {
        Session session = manager.getCurrentSession();
        session.delete(certificate);

    }

    @Override
    public void updateEntity(Certificate certificate) {
        Session session = manager.getCurrentSession();
        session.merge(certificate);
    }


    public void saveCertificatesTag(long c, long t) {
        Session session = manager.getCurrentSession();
        session.createNativeQuery("INSERT INTO certificates_tag VALUES(?, ?)")
                .setParameter(1, c)
                .setParameter(2, t)
                .executeUpdate();
    }


    public List<Certificate> getCertificateAndTag(Certificate c, Tag t) {
        Session session = manager.getCurrentSession();
        return session.createQuery("select c from Certificate c " +
                        "join c.tags t where t.id=:tId and c.id=:cId", Certificate.class)
                .setParameter("tId", t.getId())
                .setParameter("cId", c.getId()).getResultList();
    }


    public List<Certificate> getCertificatesByName(String name) {
        Session session = manager.getCurrentSession();
        return session.createQuery("select c from Certificate c  where c.certificateName like concat('%',:name,'%')", Certificate.class)
                .setParameter("name", name)
                .list();
    }

    public int countAllCertificates() {
        Session session = manager.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Certificate> root = criteriaQuery.from(Certificate.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        return session.createQuery(criteriaQuery).uniqueResult().intValue();
    }

    public List<Certificate> getCertificatesByTags(long id1, long id2) {
        Session session = manager.getCurrentSession();
        return session.createNativeQuery("select c.id,c.certificate_name from gift_certificate as c " +
                        "join certificates_tag as ct on ct.certificate_id = c.id " +
                        "join tags as t on t.id = ct.tag_id " +
                        "where t.id in (?,?) " +
                        "group by c.certificate_name", Certificate.class)
                .setParameter(1, id1)
                .setParameter(2, id2)
                .list();

    }

}



