package ru.kata.spring.boot_security.demo.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

   private final UserDao userDao;
   final PasswordEncoder bCryptPasswordEncoder;

   public UserServiceImpl(UserDao userDao, @Lazy PasswordEncoder bCryptPasswordEncoder) {
      this.userDao = userDao;
      this.bCryptPasswordEncoder = bCryptPasswordEncoder;
   }

   @Override
   @Transactional
   public void saveUser(User user) {
      user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
      user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
      userDao.saveUser(user);
   }
   @Override
   @Transactional
   public void removeUserById(long id) {
      userDao.removeUserById(id);
   }

   @Override
   @Transactional(readOnly = true)
   public List<User> getAllUsers() {
      return userDao.getAllUsers();
   }

   @Override
   @Transactional(readOnly = true)
   public User getUserById(long id) {
     return userDao.getUserById(id);
   }

   @Override
   @Transactional
   public void updateUser(User user) {
      userDao.updateUser(user);
   }

   @Override
   @Transactional(readOnly = true)
   public User getUserByUsername(String username) {
      return userDao.getUserByUsername(username);
   }

   @Override
   @Transactional
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = userDao.getUserByUsername(username);
      if(user == null){
         throw new UsernameNotFoundException(String.format("User '%s' not found", username));
      }
      return user;
   }
}
