package com.plugandroll.version1.config;

import com.plugandroll.version1.models.Forum;
import com.plugandroll.version1.models.TypeRol;
import com.plugandroll.version1.models.UserEntity;
import com.plugandroll.version1.repositories.ForumRepository;
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
    CommandLineRunner commandLineRunner(UserEntityRepository userEntityRepository, ForumRepository forumRepository) {
        return strings -> {
            userEntityRepository.deleteAll();
            forumRepository.deleteAll();

            /*================= USERS =================*/

            UserEntity master = new UserEntity("master",
                    passwordEncoder.encode("master"),
                    "master@masterchef.com",
                    true,
                    Stream.of(TypeRol.ADMIN, TypeRol.DM).collect(Collectors.toSet()));

            userEntityRepository.save(master);

            /*================= FORUM =================*/

            Forum forum1 = new Forum(Stream.of(TypeRol.ADMIN, TypeRol.DM).collect(Collectors.toSet()),
                    Stream.of("un Thread", "otro Thread").collect(Collectors.toList()));

            forumRepository.save(forum1);

        };
    }
}
