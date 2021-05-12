package com.plugandroll.version1.config;

import com.plugandroll.version1.mappers.UserDTOConverter;
import com.plugandroll.version1.models.Publication;
import com.plugandroll.version1.models.Forum;
import com.plugandroll.version1.models.TypeRol;
import com.plugandroll.version1.models.UserEntity;
import com.plugandroll.version1.models.Thread;
import com.plugandroll.version1.repositories.PublicationRepository;
import com.plugandroll.version1.repositories.ForumRepository;
import com.plugandroll.version1.repositories.ThreadRepository;
import com.plugandroll.version1.repositories.UserEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class MongoDBPopulate {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Bean
    CommandLineRunner commandLineRunner(UserEntityRepository userEntityRepository, PublicationRepository publicationRepository,
                                        ForumRepository forumRepository, ThreadRepository threadRepository) {
        return strings -> {
            userEntityRepository.deleteAll();
            publicationRepository.deleteAll();
            forumRepository.deleteAll();
            threadRepository.deleteAll();

            /*================= USERS =================*/

            UserEntity master = new UserEntity("master",
                    passwordEncoder.encode("mastermaster"),
                    "master@masterchef.com",
                    true,
                    Stream.of(TypeRol.ADMIN, TypeRol.DM).collect(Collectors.toSet()));

            userEntityRepository.save(master);

            UserEntity player1 = new UserEntity("player1",
                    passwordEncoder.encode("player1player1"),
                    "player1@masterchef.com",
                    true,
                    Stream.of(TypeRol.PLAYER).collect(Collectors.toSet()));

            userEntityRepository.save(player1);

            UserEntity dm1 = new UserEntity("dm1dm1",
                    passwordEncoder.encode("dm1dm1dm1dm1"),
                    "dm1@masterchef.com",
                    true,
                    Stream.of(TypeRol.DM).collect(Collectors.toSet()));

            userEntityRepository.save(dm1);

            /*================= FORUM =================*/

            Forum forum1 = new Forum("Admins Forum", Stream.of(TypeRol.ADMIN).collect(Collectors.toSet()));
            Forum forum2 = new Forum("DMs Forum", Stream.of(TypeRol.ADMIN, TypeRol.DM).collect(Collectors.toSet()));
            Forum forum3 = new Forum("Players Forum", Stream.of(TypeRol.ADMIN, TypeRol.PLAYER).collect(Collectors.toSet()));
            Forum forum4 = new Forum("Common Forum", Stream.of(TypeRol.ADMIN, TypeRol.DM, TypeRol.PLAYER).collect(Collectors.toSet()));

            forumRepository.save(forum1);
            forumRepository.save(forum2);
            forumRepository.save(forum3);
            forumRepository.save(forum4);

            /*================= THREAD =================*/

            Thread thread1 = new Thread("Habeis visto lo bien que...", 5, LocalDateTime.of(2020,5,21,16,05,23),
                    null, UserDTOConverter.UserToGetUserDTO(master), forum1);

            Thread thread2 = new Thread("No tiene mucho sentido, no entres", 2, LocalDateTime.of(2020,5,21,16,05,23),
                    null, UserDTOConverter.UserToGetUserDTO(master), forum2);

            threadRepository.save(thread1);
            threadRepository.save(thread2);

            /*================= PUBLICATIONS =================*/

            Publication publication1 = new Publication("Se me ha caido el pan al suelo y mi dragon se lo comió",
                    LocalDateTime.of(2021, 01, 01, 10, 11, 24),
                    UserDTOConverter.UserToGetUserDTO(master),
                    thread1
            );

            Publication publication2 = new Publication("... queda la nueva pagina de plug&roll?",
                    LocalDateTime.of(2021, 01, 01, 10, 12, 24),
                    UserDTOConverter.UserToGetUserDTO(master),
                    thread1
            );

            Publication publication3 = new Publication("Tengo un monton de personajes, pero no se jugar con ninguno",
                    LocalDateTime.of(2021, 01, 01, 10, 11, 24),
                    UserDTOConverter.UserToGetUserDTO(master),
                    thread2
            );
            publicationRepository.save(publication1);
            publicationRepository.save(publication2);
            publicationRepository.save(publication3);


        };

    }
}
