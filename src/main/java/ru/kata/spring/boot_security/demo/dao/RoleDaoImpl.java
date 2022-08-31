package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Set;


public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void addRole(Role role) {
        entityManager.persist(role);
    }


    @Override
    public Role getRoleByName(String name) {
       return entityManager.find(Role.class, name);
    }


    @Override
    public Set<Role> getAllRoles() {
       return new HashSet<>(entityManager.createQuery("from Role", Role.class)
               .getResultList());
    }
}
