package config.service;

import config.Entities.User;
import config.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService    {
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User findByUserName(String username) {
        //System.out.println(repository.findById((long) 1));
        //repository.findOne()
        Optional<User> user = Optional.ofNullable(repository.findByUsername(username));
        System.out.println("find by name"+user);
        return user.get();
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return repository.findAll();
    }
}
