package com.plugandroll.version1.config;

import com.plugandroll.version1.models.TypeRol;
import com.plugandroll.version1.models.UserEntity;
import com.plugandroll.version1.repositories.UserEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class MongoDBPopulate<E> {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Bean
    CommandLineRunner commandLineRunner(UserEntityRepository userEntityRepository) {
        return strings -> {
            userEntityRepository.deleteAll();

            /*================= USERS =================*/

            UserEntity master = new UserEntity("asdf",
                    passwordEncoder.encode("asdfasdf"),
                    "asdf@asdf.com",
                    true,
                    Stream.of(TypeRol.ADMIN, TypeRol.DM).collect(Collectors.toSet()));

            userEntityRepository.save(master);

        };
    }
}
