
package com.aiinterview.platform.service;

import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aiinterview.platform.dto.LoginRequest;
import com.aiinterview.platform.dto.LoginResponse;
import com.aiinterview.platform.dto.RegisterRequest;
import com.aiinterview.platform.dto.RegisterResponse;
import com.aiinterview.platform.entity.User;
import com.aiinterview.platform.exception.EmailAlreadyExistsException;
import com.aiinterview.platform.exception.InvalidCredentialsException;
import com.aiinterview.platform.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService=jwtService;
    }

    public RegisterResponse register(RegisterRequest request) {
        User user = new User();
        user.setName(request.name());

        if (!userRepository.existsByEmail(request.email())) {
            user.setEmail(request.email());
        } else {
            throw new EmailAlreadyExistsException("Email Already Exists");
        }
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("CANDIDATE");
        User savedUser = userRepository.save(user);

        return new RegisterResponse("Registration Successful", savedUser.getId(), savedUser.getEmail());
    }

    public LoginResponse login(LoginRequest request){
       User user=userRepository.findByEmail(request.email())
                .orElseThrow(()->
                              new InvalidCredentialsException("Invalid email or password"));


        if(!passwordEncoder.matches(request.password(),user.getPassword())){
              throw new InvalidCredentialsException("Invalid email or password");
        }
        String token=jwtService.generateToken(user);
        return new LoginResponse("Login Successful",user.getId(),token);
    }
}