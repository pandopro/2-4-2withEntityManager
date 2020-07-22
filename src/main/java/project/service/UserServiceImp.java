package project.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dao.UserDAO;
import project.model.Role;
import project.model.User;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class UserServiceImp implements UserService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    @Qualifier("userEntityManager")
    private UserDAO userDao;

    public UserServiceImp() {
    }

    @Transactional
    public void add(User user) {
        this.userDao.add(user);
    }

    public List<User> listUsers() {
        return this.userDao.listUsers();
    }

    @Transactional
    @Override
    public boolean remove(long id) {
        return this.userDao.remove(id);
    }


    @Override
    public User getUser(long id) {
        return userDao.getUser(id);
    }

    @Override
    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Transactional
    @Override
    public boolean edit(long id, User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User olduser = getUser(id);
        user.setRoles(olduser.getRoles());
        return this.userDao.edit(id, user);
    }

    public boolean demo() {
        HashSet<Role> roles1 = new HashSet<Role>();
        roles1.add(new Role("ADMIN"));
        roles1.add(new Role("USER"));
        userDao.add(new User("ADMIN", "SERVER", "ADMIN", passwordEncoder.encode("ADMIN"), Collections.singleton(new Role("ADMIN"))));
        userDao.add(new User("Alex", "Detroit", "ihbb", passwordEncoder.encode("123"), Collections.singleton(new Role("USER"))));
        userDao.add(new User("Anton", "Chelyabinsk", "qwerty@qwerty.ru", passwordEncoder.encode("123"), roles1));
        userDao.add(new User("Igor", "Omsk", "igorOmsk@qwerty.ru", passwordEncoder.encode("123"), Collections.singleton(new Role("USER"))));
        userDao.add(new User("Leska", "Murmansk", "leshka@qwerty.ru", passwordEncoder.encode("123"), Collections.singleton(new Role("ADMIN"))));
        return true;
    }
}