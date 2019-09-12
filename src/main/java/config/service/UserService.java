package config.service;

import config.Entities.User;

import java.util.List;

public interface UserService {
    User findByUserName(String username);

    List<User> findAll();
}
