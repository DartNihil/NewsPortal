package com.example.newsportal.service;

import com.example.newsportal.entity.Category;
import com.example.newsportal.entity.User;
import com.example.newsportal.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String channelName) throws UsernameNotFoundException {
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

    public Map<Category, Integer> ratePreferences(User user, Category category) {
        Map<Category, Integer> preferences = user.getPreferences();
        preferences.put(category, preferences.get(category) + 1);
        userRepository.save(user);
        return preferences;
    }
}
