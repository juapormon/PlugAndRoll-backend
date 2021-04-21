package com.plugandroll.version1.security;

import com.plugandroll.version1.models.UserEntity;
import com.plugandroll.version1.repositories.UserEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserEntityRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user;

        user = repository.findByUsername(username).orElse(null);

        if(user != null) {
            return User//
                    .withUsername(username)//
                    .password(user.getPassword())//
                    .authorities(user.getAuthorities())//
                    .accountExpired(false)//
                    .accountLocked(false)//
                    .credentialsExpired(false)//
                    .disabled(false)//
                    .build();
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }
}
