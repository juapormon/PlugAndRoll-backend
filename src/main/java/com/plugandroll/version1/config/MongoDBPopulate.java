package com.plugandroll.version1.config;

import com.plugandroll.version1.models.TypeRol;
import com.plugandroll.version1.models.UserEntity;
import com.plugandroll.version1.repositories.RedBoxRepository;
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
    CommandLineRunner commandLineRunner(UserEntityRepository userEntityRepository, RedBoxRepository redBoxRepository) {
        return strings -> {
            userEntityRepository.deleteAll();
            redBoxRepository.deleteAll();

            /*================= USERS =================*/

            UserEntity asdf = new UserEntity("asdf",
                    passwordEncoder.encode("asdfasdf"),
                    "asdf@asdf.com",
                    true,
                    Stream.of(TypeRol.ADMIN, TypeRol.DM, TypeRol.PLAYER).collect(Collectors.toSet()));

            UserEntity fdsa = new UserEntity("fdsa",
                    passwordEncoder.encode("asdfasdf"),
                    "fdsa@fdsa.com",
                    true,
                    Stream.of(TypeRol.DM, TypeRol.PLAYER).collect(Collectors.toSet()));

            userEntityRepository.save(asdf);
            userEntityRepository.save(fdsa);

        };
    }
}
