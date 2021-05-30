package com.plugandroll.version1.config;

import com.plugandroll.version1.mappers.UserDTOConverter;
import com.plugandroll.version1.models.*;
import com.plugandroll.version1.models.Thread;
import com.plugandroll.version1.repositories.*;
import org.assertj.core.util.Lists;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class MongoDBPopulate {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Bean
    CommandLineRunner commandLineRunner(UserEntityRepository userEntityRepository, PublicationRepository publicationRepository,
                                        ForumRepository forumRepository, ThreadRepository threadRepository,
                                        SpamWordRepository spamWordRepository, OfferRepository offerRepository,
                                        ApplicationRepostiory applicationRepostiory) {
        return strings -> {
            userEntityRepository.deleteAll();
            publicationRepository.deleteAll();
            forumRepository.deleteAll();
            threadRepository.deleteAll();
            spamWordRepository.deleteAll();
            offerRepository.deleteAll();
            applicationRepostiory.deleteAll();

            /*================= USERS =================*/

            UserEntity master = new UserEntity("master",
                    passwordEncoder.encode("mastermaster"),
                    "master@masterchef.com",
                    true,
                    Stream.of(TypeRol.ADMIN, TypeRol.DM).collect(Collectors.toSet()),
                    2.5,
                    123);

            userEntityRepository.save(master);

            UserEntity player1 = new UserEntity("player1",
                    passwordEncoder.encode("player1player1"),
                    "player1@masterchef.com",
                    true,
                    Stream.of(TypeRol.PLAYER).collect(Collectors.toSet()),
                    0.0,
                    12);

            userEntityRepository.save(player1);

            UserEntity dm1 = new UserEntity("dm1dm1",
                    passwordEncoder.encode("dm1dm1dm1dm1"),
                    "dm1@masterchef.com",
                    true,
                    Stream.of(TypeRol.DM).collect(Collectors.toSet()),
                    5.0,
                    11);

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
                    null, UserDTOConverter.UserToGetUserDTO(master), false, forum1);

            Thread thread2 = new Thread("No tiene mucho sentido, no entres", 2, LocalDateTime.of(2020,5,21,16,05,23),
                    null, UserDTOConverter.UserToGetUserDTO(master), false, forum3);

            Thread thread3 = new Thread("solo los dms podemos verlo", 2, LocalDateTime.of(2020,5,21,16,05,23),
                    null, UserDTOConverter.UserToGetUserDTO(master), false, forum2);

            Thread thread4 = new Thread("los no registrados no veran esto", 2, LocalDateTime.of(2020,5,21,16,05,23),
                    null, UserDTOConverter.UserToGetUserDTO(master), true, forum4);

            Thread thread5 = new Thread("Que palizaaaa", 2, LocalDateTime.of(2020,5,21,16,05,23),
                    null, UserDTOConverter.UserToGetUserDTO(master), false, forum4);

            threadRepository.save(thread1);
            threadRepository.save(thread2);
            threadRepository.save(thread3);
            threadRepository.save(thread4);
            threadRepository.save(thread5);

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

            Publication publication4 = new Publication("vaya paliza le pego mayweather al otro",
                    LocalDateTime.of(2021, 01, 01, 10, 11, 24),
                    UserDTOConverter.UserToGetUserDTO(master),
                    thread5
            );

            Publication publication5 = new Publication("deberias dejar de poner cosas que no tienen que ver con el foro menso",
                    LocalDateTime.of(2021, 01, 01, 10, 11, 24),
                    UserDTOConverter.UserToGetUserDTO(master),
                    thread5
            );

            Publication publication6 = new Publication("Lo ven admins y dms",
                    LocalDateTime.of(2021, 01, 01, 10, 11, 24),
                    UserDTOConverter.UserToGetUserDTO(master),
                    thread3
            );

            Publication publication7 = new Publication("Lo ven admins y players",
                    LocalDateTime.of(2021, 01, 01, 10, 11, 24),
                    UserDTOConverter.UserToGetUserDTO(master),
                    thread2
            );

            Publication publication8 = new Publication("solo lo ven admins",
                    LocalDateTime.of(2021, 01, 01, 10, 11, 24),
                    UserDTOConverter.UserToGetUserDTO(master),
                    thread1
            );
            publicationRepository.save(publication1);
            publicationRepository.save(publication2);
            publicationRepository.save(publication3);
            publicationRepository.save(publication4);
            publicationRepository.save(publication5);
            publicationRepository.save(publication6);
            publicationRepository.save(publication7);
            publicationRepository.save(publication8);

            /*================= Spam Words =================*/

            SpamWord spam1 = new SpamWord("sex");
            SpamWord spam2 = new SpamWord("nigga");
            SpamWord spam3 = new SpamWord("handjob");
            SpamWord spam4 = new SpamWord("porn");
            SpamWord spam5 = new SpamWord("nigger");
            SpamWord spam6 = new SpamWord("asshole");
            SpamWord spam7 = new SpamWord("blowjob");
            SpamWord spam8 = new SpamWord("cunt");
            SpamWord spam9 = new SpamWord("cum");
            SpamWord spam10 = new SpamWord("fuck");

            spamWordRepository.saveAll(Lists.list(spam1, spam2, spam3, spam4, spam5,
                    spam6,spam7,spam8,spam9,spam10));

            /*================= Offers =================*/

            CoachingOffer offer1 = new CoachingOffer("Coaching in game for D&D- 1h",
                    "It's going to be an hour coaching you how to play this game",
                    CoachingType.DM, 10.00, UserDTOConverter.UserToGetUserDTO(master));
            CoachingOffer offer2 = new CoachingOffer("I'll coach you if you let me",
                    "It's going to be an hour coaching you how to play this game",
                    CoachingType.PLAYER, 40.00,UserDTOConverter.UserToGetUserDTO(player1));
            CoachingOffer offer3 = new CoachingOffer("Coach for begginers. Includes Spells, levels, items, etc.. You will be amazed",
                    "It's going to be an hour coaching you how to play this game",
                    CoachingType.PLAYER, 15.00, UserDTOConverter.UserToGetUserDTO(player1));
            CoachingOffer offer4 = new CoachingOffer("Advanced Dms coaching",
                    "It's going to be an hour coaching you how to play this game",
                    CoachingType.DM, 10.00,UserDTOConverter.UserToGetUserDTO(dm1));


            offerRepository.saveAll(Lists.list(offer1, offer2, offer3, offer4));


            /*================= Applications =================*/

            Application application1 = new Application(master.getUsername(), offer1, LocalDate.now(), false);
            Application application2 = new Application(master.getUsername(), offer1, LocalDate.now(),true);
            Application application3 = new Application(master.getUsername(), offer3, LocalDate.now(), false);
            Application application4 = new Application(master.getUsername(), offer4, LocalDate.now(),false);


            applicationRepostiory.saveAll(Lists.list(application1, application2, application3, application4));
        };

    }
}
