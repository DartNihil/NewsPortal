package com.example.newsportal.service;

import com.example.newsportal.entity.User;
import com.example.newsportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    private PostService postService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public boolean exists(String channelName, String password) throws UsernameNotFoundException {
        Optional<User> byUsername = userRepository.findByChannelName(channelName);
        if (byUsername.isPresent()) {
            User user = byUsername.get();
            return password.equals(user.getPassword());
        } else {
            return false;
        }
    }

    public Optional<User> findUser(String channelName) throws UsernameNotFoundException {
        return userRepository.findByChannelName(channelName);
    }

}
