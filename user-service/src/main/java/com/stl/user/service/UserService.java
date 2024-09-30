package com.stl.user.service;

import com.stl.user.dto.LoginUserDTO;
import com.stl.user.dto.UserDTO;
import com.stl.user.entity.User;
import com.stl.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final AuthenticationManager authenticationManager;

    public User register(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setAccNumber(userDTO.getAccNumber());
        user.setRole("USER");
        return userRepository.save(user);
    }

    public User login(LoginUserDTO loginUserDTO){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDTO.getEmail(), loginUserDTO.getPassword()));
        return (User) userRepository.findByEmail(loginUserDTO.getEmail()).get();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Object getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<Object> findById(Long id) {
        return Optional.of(userRepository.findById(id));
    }
}

