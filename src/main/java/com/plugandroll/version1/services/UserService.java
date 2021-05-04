package com.plugandroll.version1.services;

import com.plugandroll.version1.models.UserEntity;
import com.plugandroll.version1.repositories.UserEntityRepository;
import com.plugandroll.version1.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;
    private UserEntityRepository userEntityRepository;

    public List<UserEntity> getAll() {
        return userEntityRepository.findAll();

    }

    public UserEntity updateUser(UserEntity userToUpdate) {
        return this.userEntityRepository.save(userToUpdate);
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

    public UserEntity findByUsername(String username) {
        return this.userEntityRepository.findByUsername(username).orElse(null);
    }
}
