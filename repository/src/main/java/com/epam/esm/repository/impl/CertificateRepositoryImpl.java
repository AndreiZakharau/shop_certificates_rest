package com.epam.esm.repository.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Certificate_;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.Tag_;
import com.epam.esm.repository.CertificateRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

    @Autowired
    private SessionFactory manager;

    @Override
    public List<Certificate> getAllEntity(int limit, int offset) {
        Session session = manager.getCurrentSession();
        /*
         * query using hql
         */
//        return session.createQuery("select c from Certificate c", Certificate.class)
//                .setMaxResults(limit)
//                .setFirstResult(offset)
//
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Certificate> cq = cb.createQuery(Certificate.class);
        cq.from(Certificate.class);
        return session.createQuery(cq)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();

    }

    @Override
    public Optional<Certificate> getEntityById(long id) {
        Session session = manager.getCurrentSession();
        /*
         * query using hql
         */
//        return session
//                .createQuery("select c from Certificate c where c.id = :id order by c.id ", Certificate.class)
//                .setParameter("id", id).uniqueResultOptional();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Certificate> cq = cb.createQuery(Certificate.class);
        Root<Certificate> certificate = cq.from(Certificate.class);
        cq.select(certificate).where(cb.equal(certificate.get("id"), id)).orderBy(cb.asc(certificate.get("id")));
        return session.createQuery(cq)
                .uniqueResultOptional();
    }

    @Override
    public void addEntity(Certificate certificate) {
        Session session = manager.getCurrentSession();
        session.save(certificate);
    }

    @Override
    public void deleteEntity(Certificate certificate) {
        Session session = manager.getCurrentSession();
        session.delete(certificate);

    }

    @Override
    public void updateEntity(Certificate certificate) {
        Session session = manager.getCurrentSession();
        session.merge(certificate);
    }


    @Override
    public void saveCertificatesTag(long c, long t) {
        Session session = manager.getCurrentSession();
        session.createNativeQuery("INSERT INTO certificates_tag VALUES(?, ?)")
                .setParameter(1, c)
                .setParameter(2, t)
                .executeUpdate();
    }


    @Override
    public List<Certificate> getCertificateAndTag(Certificate c, Tag t) {
        Session session = manager.getCurrentSession();
        /*
         * query using hql
         */
//        return session.createQuery("select c from Certificate c " +
//                        "join c.tags t where t.id=:tId and c.id=:cId", Certificate.class)
//                .setParameter("tId", t.getId())
//                .setParameter("cId", c.getId()).getResultList();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Certificate> cq = cb.createQuery(Certificate.class);
        Root<Certificate> certificate = cq.from(Certificate.class);
        Join<Certificate, Tag> tag = certificate.join("id", JoinType.INNER);
        cq.select(certificate).where(cb.equal(certificate.get(Certificate_.ID), c.getId()), (cb.equal(tag.get(Tag_.ID), t.getId())));
        return session.createQuery(cq).getResultList();
    }


    @Override
    public List<Certificate> getCertificatesByName(String name) {
        Session session = manager.getCurrentSession();
//        return session.createQuery("select c from Certificate c  where c.certificateName like concat('%',:name,'%')", Certificate.class)
//                .setParameter("name", name)
//                .list();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Certificate> cq = cb.createQuery(Certificate.class);
        Root<Certificate> certificate = cq.from(Certificate.class);
        cq.select(certificate).where(cb.like(certificate.get(Certificate_.CERTIFICATE_NAME), "%" + name + "%"));
        return session.createQuery(cq).getResultList();
    }

    @Override
    public int countAllCertificates() {
        Session session = manager.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Certificate> root = criteriaQuery.from(Certificate.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        return session.createQuery(criteriaQuery).uniqueResult().intValue();
    }

    @Override
    public List<Certificate> getCertificateByParameters(String name, List<String> tagNames, String description,
                                                        List<Double> prices, Integer page, Integer size) {
        Session session = manager.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Certificate> cq = cb.createQuery(Certificate.class);
        Root<Certificate> certificate = cq.from(Certificate.class);
        Join<Certificate, Tag> tag = certificate.join(Certificate_.TAGS);
        List<Predicate> predicates = new ArrayList<>();
        if (name != null) {
            predicates.add(cb.like(certificate.get(Certificate_.CERTIFICATE_NAME), "%" + name + "%"));
        }
        for (String tagName : tagNames) {
            if (name != null) {
                predicates.add(cb.equal(tag.get(Tag_.TAG_NAME), tagName));
            }
        }
        if (description != null) {
            predicates.add(cb.like(certificate.get(Certificate_.DESCRIPTION), "%" + description + "%"));
        }
        if (prices.size() != 0) {
            Double maxPrice = prices.stream().mapToDouble(price -> price)
                    .max().orElseThrow();
            Double minPrice = prices.stream().mapToDouble(price -> price)
                    .min().orElseThrow();
            predicates.add(cb.between(certificate.get(Certificate_.PRICE),minPrice,maxPrice));
        }

        cq.select(certificate).where(predicates.toArray(Predicate[]::new)).groupBy(certificate.get(Certificate_.CERTIFICATE_NAME));
        return session.createQuery(cq)
                .setMaxResults(size)
                .setFirstResult(size * (page-1))
                .getResultList();
    }

    @Override
    public List<Certificate> getCertificatesByTags(List<String> tagNames) {
        Session session = manager.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Certificate> cq = cb.createQuery(Certificate.class);
        Root<Certificate> certificate = cq.from(Certificate.class);
        Join<Certificate, Tag> tag = certificate.join(Certificate_.TAGS);
        List<Predicate>predicates = new ArrayList<>();
        if(tagNames.size()!=0) {
            for (String tagName : tagNames) {
                predicates.add(cb.equal(tag.get(Tag_.TAG_NAME), tagName));
            }
        }
        cq.select(certificate).where(cb.or(predicates.toArray(Predicate[]::new))).groupBy(certificate.get(Certificate_.CERTIFICATE_NAME));
        return session.createQuery(cq).getResultList();
    }

}



