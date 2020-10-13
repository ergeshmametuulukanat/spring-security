package com.company.dao;

import com.company.model.Role;
import com.company.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RoleDao roleDao;

    @Override
    @Transactional
    public List<User> getAllUsers() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> rootEntry = cq.from(User.class);
        CriteriaQuery<User> all = cq.select(rootEntry);
        TypedQuery<User> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    @Transactional
    public void add(User user) {
        user.setRoles(new HashSet<>(Collections.singletonList(getRole())));
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void delete(User user) {
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
    }

    @Override
    @Transactional
    public void update(User user) {
        user.setRoles(new HashSet<>(Collections.singletonList(getRole())));
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public User getById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByUsername(String username) {
        List<User> users = getAllUsers();
        return users.stream().filter(x -> x.getName().equals(username)).findAny().get();
    }

    private Role getRole() {
        List<Role> roles = roleDao.getAllRoles();
        for (Role role : roles) {
            if (role.getRole().equals("ROLE_USER")) {
                return role;
            }
        }
        return null;
    }
}
