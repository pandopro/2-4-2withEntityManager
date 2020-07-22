package project.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserEntityManager implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Transactional
    @Override
    public List<User> listUsers() {
        Query query = entityManager.createQuery("From User");

        // Query query = getEntityManager().createQuery("select c from Car c");
        List<User> resultList = query.getResultList();
        return resultList;

    }


    @Override
    public Boolean remove(long id) {
        try {
            User user = entityManager.find(User.class, id);
            if (user != null) {
                entityManager.remove(user);

            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean edit(long id, User user) {
        try {
            entityManager.merge(user);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUser(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByLogin(String login) {
        String hql = "FROM User user WHERE user.email= :email";
        User user = (User) entityManager.createQuery(hql).setParameter("email", login).getSingleResult();
        return user;
    }

//    public EntityManager getEntityManager() {
//        return entityManager;
//    }
//
//    @PersistenceContext
//    public void setEntityManager(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }


}

