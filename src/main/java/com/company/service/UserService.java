package com.company.service;

import com.company.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void add(User user);
    void delete(User user);
    void update(User user);
    User getById(long id);
}
