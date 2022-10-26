package zakharau.com.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import zakharau.com.connect.DBConnect;
import zakharau.com.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements  Serializable {

//    @Autowired
//    private SessionFactory manager;


//    public List<User> getAllEntity(int limit, int offset) {
//        Session session = manager.getCurrentSession();
//        return session.createQuery("select u from User u", User.class).getResultList();
//    }


//    public Optional<User> getEntityById(long id) {
//        Session session = manager.getCurrentSession();
//        return session.createQuery("select u from User u where u.id =:id ", User.class)
//                .setParameter("id", id)
//                .getResultList().stream().findFirst();
//    }


    public void addEntity(User user) {
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO users(nick_name,email) VALUES (?,?)")) {

            preparedStatement.setString(1, user.getNickName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

//    public void deleteEntity(long id) {
//        Session session = manager.getCurrentSession();
//        session.createQuery("delete from User where id =:id")
//                .setParameter("id", id);
//    }
//
//
//    public void updateEntity(User user) {
//        Session session = manager.getCurrentSession();
//        session.update(user);
//    }
//
//    public Optional<User> getUserByName(String name) {
//        Session session = manager.getCurrentSession();
//        return session.createQuery("select u from User as u where nickName =:name", User.class)
//                .setParameter("name", name)
//                .getResultList().stream().findFirst();
//    }
//
//    public int countAllUsers() {
//        Session session = manager.getCurrentSession();
//        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
//        Root<User> tagRoot = criteriaQuery.from(User.class);
//        criteriaQuery.select(criteriaBuilder.count(tagRoot));
//        return session.createQuery(criteriaQuery).uniqueResult().intValue();
//    }

}
