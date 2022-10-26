package zakharau.com.repository.impl;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import zakharau.com.connect.DBConnect;
import zakharau.com.entity.Certificate;
import zakharau.com.entity.Tag;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class CertificateRepositoryImpl implements Serializable {

//    @Autowired
//    private SessionFactory manager;
//
//
//    public List<Certificate> getAllEntity(int limit, int offset) {
//        Session session = manager.getCurrentSession();
//        return session.createQuery("select c from Certificate c", Certificate.class)
//                .setMaxResults(limit)
//                .setFirstResult(offset)
//                .getResultList();
//    }
//
//    public Optional<Certificate> getEntityById(long id) {
//        Session session = manager.getCurrentSession();
//        return session
//                .createQuery("select c from Certificate c where c.id = :id order by c.id ", Certificate.class)
//                .setParameter("id", id).uniqueResultOptional();
//    }
//
    public void addEntity(Certificate certificate) {
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO gift_certificate(certificate_name,description,price,duration,create_date,last_update_date) VALUES (?,?,?,?,?,?)")) {

            preparedStatement.setString(1,certificate.getCertificateName() );
            preparedStatement.setString(2, certificate.getDescription());
            preparedStatement.setDouble(3, certificate.getPrice());
            preparedStatement.setInt(4, certificate.getDuration());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(certificate.getCreateDate()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(certificate.getLastUpdateDate()));
            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

//
//    public void deleteEntity(Certificate certificate) {
//        Session session = manager.getCurrentSession();
//        session.delete(certificate);
//
//    }
//
//    public void updateEntity(Certificate certificate) {
//        Session session = manager.getCurrentSession();
//        session.merge(certificate);
//    }
//
//
//    public void saveCertificatesTag(long c, long t) {
//        Session session = manager.getCurrentSession();
//        session.createNativeQuery("INSERT INTO certificates_tag VALUES(?, ?)")
//                .setParameter(1, c)
//                .setParameter(2, t)
//                .executeUpdate();
//    }
//
//
//    public List<Certificate> getCertificateAndTag(Certificate c, Tag t) {
//        Session session = manager.getCurrentSession();
//        return session.createQuery("select c from Certificate c " +
//                        "join c.tags t where t.id=:tId and c.id=:cId", Certificate.class)
//                .setParameter("tId", t.getId())
//                .setParameter("cId", c.getId()).getResultList();
//    }
//
//
//    public List<Certificate> getCertificatesByName(String name) {
//        Session session = manager.getCurrentSession();
//        return session.createQuery("select c from Certificate c  where c.certificateName like concat('%',:name,'%')", Certificate.class)
//                .setParameter("name", name)
//                .list();
//    }
//
//    public int countAllCertificates() {
//        Session session = manager.getCurrentSession();
//        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
//        Root<Certificate> root = criteriaQuery.from(Certificate.class);
//        criteriaQuery.select(criteriaBuilder.count(root));
//        return session.createQuery(criteriaQuery).uniqueResult().intValue();
//
//    }
//
//    public List<Certificate> getCertificatesByTags(long id1, long id2) {
//        Session session = manager.getCurrentSession();
//        return session.createNativeQuery("select c.id from gift_certificate as c " +
//                        "join certificates_tag as ct on ct.certificate_id = c.id " +
//                        "join tags as t on t.id = ct.tag_id " +
//                        "where t.id in (?,?) " +
//                        "group by c.certificate_name", Certificate.class)
//                .setParameter(1, id1)
//                .setParameter(2, id2)
//                .list();
//    }

}



