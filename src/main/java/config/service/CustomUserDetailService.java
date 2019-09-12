package config.service;

import config.Entities.CustomUserDetails;
import config.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import config.repositories.UserRepository;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public CustomUserDetailService( UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = Optional.ofNullable(userService.findByUserName(username));
        System.out.println("load"+optionalUser.isPresent());
        optionalUser.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        optionalUser.ifPresent(u -> System.out.println(u.getUsername() + u.getPassword()));

        return optionalUser
                .map(CustomUserDetails::new).get();
    }
}
