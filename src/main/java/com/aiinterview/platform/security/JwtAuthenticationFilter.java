package com.aiinterview.platform.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aiinterview.platform.entity.User;
import com.aiinterview.platform.exception.InvalidCredentialsException;
import com.aiinterview.platform.repository.UserRepository;
import com.aiinterview.platform.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService,UserRepository userRepository){
        this.jwtService=jwtService;
        this.userRepository=userRepository;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
      
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if(!jwtService.validateToken(token)){
                return;
            }
            String email=jwtService.extractEmail(token);
             User user=userRepository.findByEmail(email)
             .orElseThrow(() -> new InvalidCredentialsException("User not found"));
                SimpleGrantedAuthority authority=new SimpleGrantedAuthority("ROLE_"+user.getRole());
                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(user, null, List.of(authority));
               
                SecurityContextHolder.getContext().setAuthentication(authentication);

        }
          filterChain.doFilter(request, response);
    }
}
