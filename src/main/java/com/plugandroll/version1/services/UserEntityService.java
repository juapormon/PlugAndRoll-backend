package com.plugandroll.version1.services;

import com.plugandroll.version1.models.UserEntity;
import com.plugandroll.version1.repositories.UserEntityRepository;
import com.plugandroll.version1.security.JwtTokenProvider;
import io.jsonwebtoken.lang.Assert;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserEntityService {

    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;
    private UserEntityRepository userEntityRepository;

    public UserEntity signup(UserEntity user) {
        Assert.notNull(user);
        if (this.userEntityRepository.findByUsername(user.getUsername()).isPresent())
            throw new IllegalArgumentException("User already exists");
        if (this.userEntityRepository.findByEmail(user.getEmail()).isPresent())
            throw new IllegalArgumentException("User already exists");
        user.setPassword(new BCryptPasswordEncoder(12).encode(user.getPassword()));
        user.setRoles(user.getRoles().stream().collect(Collectors.toSet()));
        user.setRating(null);
        user.setCoachedGames(0);
        this.userEntityRepository.save(user);
        return user;
    }

    public String login(String username, String password) {
        UserEntity user;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            user = this.userEntityRepository.findByUsername(username).orElse(null);
            this.updateUser(user);
            return jwtTokenProvider.createToken(username, user.getId(), user.getRoles());
        } catch (AuthenticationException e) {
            throw new IllegalArgumentException();
        }
    }

    public List<UserEntity> getAll() {
        return userEntityRepository.findAll();
    }

    public UserEntity findByUsername(String username) {
        return this.userEntityRepository.findByUsername(username).orElse(null);
    }

    public UserEntity updateUser(UserEntity userToUpdate) {
        return this.userEntityRepository.save(userToUpdate);
    }
}
