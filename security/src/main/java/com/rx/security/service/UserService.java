package com.rx.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rx.security.model.User;
import com.rx.security.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public boolean addNewUser(User user) throws Exception{
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //user.setRoles(UserRoles.ROLE_USER.name());
        repository.save(user);
        return true;
    }
}
