package com.example.newsportal.configuration.jwt;

import com.example.newsportal.entity.User;
import com.example.newsportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;

    private Authentication getAuthentication(String token) {
        UserDetails userDetails = userService.loadUserByUsername(jwtProvider.getLoginFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtProvider.resolveToken(request);
        if (token != null && jwtProvider.validatorToken(token)) {
            Authentication auth = getAuthentication(token);
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
                User user = (User) auth.getDetails();
                request.setAttribute("user", user);
            }
        }
        filterChain.doFilter(request, response);
    }
}
