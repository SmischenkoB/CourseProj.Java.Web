package config.service;

import config.Entities.User;
import config.repositories.RoleRepository;
import config.repositories.TestRepository;
import config.repositories.UserRepository;
import config.repositories.UserTOTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UniversalService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TestRepository testRepository;

    @Autowired
    UserTOTestRepository userTOTestRepository;


    public User findUserByUsername(String username)
    {
       return userRepository.findByUsername(username);
    }

}
