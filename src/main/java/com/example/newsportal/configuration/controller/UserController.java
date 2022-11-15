package com.example.newsportal.configuration.controller;

import com.example.newsportal.configuration.jwt.JwtProvider;
import com.example.newsportal.dto.AuthDTO;
import com.example.newsportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/authentication")
    public ResponseEntity<String> login(@RequestBody AuthDTO authDTO) {
        if (userService.exists(authDTO.getChannelName(), authDTO.getPassword())) {
            String s = jwtProvider.generationToken(authDTO.getChannelName());
            return ResponseEntity.ok(s);
        }
        return ResponseEntity.badRequest().build();
    }

}
