package com.plugandroll.version1.config;

import com.plugandroll.version1.mappers.UserDTOConverter;
import com.plugandroll.version1.models.*;
import com.plugandroll.version1.models.Thread;
import com.plugandroll.version1.repositories.*;
import org.apache.commons.io.FileUtils;
import org.assertj.core.util.Lists;
import com.plugandroll.version1.models.TypeRol;
import com.plugandroll.version1.models.UserEntity;
import com.plugandroll.version1.repositories.RedBoxRepository;
import com.plugandroll.version1.repositories.UserEntityRepository;
import org.bson.internal.Base64;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class MongoDBPopulate {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Bean
    CommandLineRunner commandLineRunner(UserEntityRepository userEntityRepository, PublicationRepository publicationRepository,
                                        ForumRepository forumRepository, ThreadRepository threadRepository,
                                        SpamWordRepository spamWordRepository, OfferRepository offerRepository,
                                        ApplicationRepostiory applicationRepostiory, RedBoxRepository redBoxRepository) {
        return strings -> {
            userEntityRepository.deleteAll();
            publicationRepository.deleteAll();
            forumRepository.deleteAll();
            threadRepository.deleteAll();
            spamWordRepository.deleteAll();
            offerRepository.deleteAll();
            applicationRepostiory.deleteAll();
            redBoxRepository.deleteAll();

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
                    3.0,
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

            /*================= RED BOX =================*/

            RedBox r1 = new RedBox(
                    "Initial Red Box",
                    Base64.encode(FileUtils.readFileToByteArray(new File("src/main/java/com/plugandroll/version1/config/InitialRedBoxes/RedBox1/story.txt"))),
                    Arrays.asList(Base64.encode(FileUtils.readFileToByteArray(new File("src/main/java/com/plugandroll/version1/config/InitialRedBoxes/RedBox1/village.jpg"))), Base64.encode(FileUtils.readFileToByteArray(new File("src/main/java/com/plugandroll/version1/config/InitialRedBoxes/RedBox1/combatVillage.jpg"))), Base64.encode(FileUtils.readFileToByteArray(new File("src/main/java/com/plugandroll/version1/config/InitialRedBoxes/RedBox1/corruptedForest.png"))), Base64.encode(FileUtils.readFileToByteArray(new File("src/main/java/com/plugandroll/version1/config/InitialRedBoxes/RedBox1/dungeon.jpeg"))), Base64.encode(FileUtils.readFileToByteArray(new File("src/main/java/com/plugandroll/version1/config/InitialRedBoxes/RedBox1/villageCenter.jpg")))),
                    Arrays.asList(Base64.encode(FileUtils.readFileToByteArray(new File("src/main/java/com/plugandroll/version1/config/InitialRedBoxes/RedBox1/ambient.mp3"))), Base64.encode(FileUtils.readFileToByteArray(new File("src/main/java/com/plugandroll/version1/config/InitialRedBoxes/RedBox1/combat.mp3")))),
                    Arrays.asList(Base64.encode(FileUtils.readFileToByteArray(new File("src/main/java/com/plugandroll/version1/config/InitialRedBoxes/RedBox1/thug.png"))), Base64.encode(FileUtils.readFileToByteArray(new File("src/main/java/com/plugandroll/version1/config/InitialRedBoxes/RedBox1/ghoul.png"))), Base64.encode(FileUtils.readFileToByteArray(new File("src/main/java/com/plugandroll/version1/config/InitialRedBoxes/RedBox1/mayor.png")))),
                    Arrays.asList(Base64.encode(FileUtils.readFileToByteArray(new File("src/main/java/com/plugandroll/version1/config/InitialRedBoxes/RedBox1/5e_level_4_dragonborn_fighter.pdf"))), Base64.encode(FileUtils.readFileToByteArray(new File("src/main/java/com/plugandroll/version1/config/InitialRedBoxes/RedBox1/5e_lvl_4_half_elf_bard_college_of_lore.pdf")))),
                    Arrays.asList(Base64.encode(FileUtils.readFileToByteArray(new File("src/main/java/com/plugandroll/version1/config/InitialRedBoxes/RedBox1/thugToken.png"))), Base64.encode(FileUtils.readFileToByteArray(new File("src/main/java/com/plugandroll/version1/config/InitialRedBoxes/RedBox1/ghoulToken.png"))), Base64.encode(FileUtils.readFileToByteArray(new File("src/main/java/com/plugandroll/version1/config/InitialRedBoxes/RedBox1/mayorToken.png")))),
                    UserDTOConverter.UserToGetUserDTO(master)
            );

            redBoxRepository.save(r1);

            /*================= THREAD =================*/

            Thread thread1 = new Thread("Thread for all kind of publications", 4.6, LocalDateTime.of(2020,5,21,16,05,23),
                    null, UserDTOConverter.UserToGetUserDTO(master), false, forum1);

            Thread thread2 = new Thread("You shouldn't enter here unless you want to talk about good plays", 2.0, LocalDateTime.of(2020,5,21,16,05,23),
                    null, UserDTOConverter.UserToGetUserDTO(master), false, forum3);

            Thread thread3 = new Thread("Only DMs can see this", 3.0, LocalDateTime.of(2020,5,21,16,05,23),
                    null, UserDTOConverter.UserToGetUserDTO(master), false, forum2);

            Thread thread4 = new Thread("If your're registered, you must see this thread title", 5.0, LocalDateTime.of(2020,5,21,16,05,23),
                    LocalDateTime.of(2021,5,21,16,05,23), UserDTOConverter.UserToGetUserDTO(master), true, forum4);

            Thread thread5 = new Thread("All users can enter here to introduce themselves", 1.0, LocalDateTime.of(2020,5,21,16,05,23),
                    null, UserDTOConverter.UserToGetUserDTO(master), false, forum4);

            threadRepository.save(thread1);
            threadRepository.save(thread2);
            threadRepository.save(thread3);
            threadRepository.save(thread4);
            threadRepository.save(thread5);

            /*================= PUBLICATIONS =================*/

            Publication publication1 = new Publication("I always have bread with me for my games",
                    LocalDateTime.of(2021, 01, 01, 10, 11, 24),
                    UserDTOConverter.UserToGetUserDTO(master),
                    thread1
            );

            Publication publication2 = new Publication("The thread title sounds amazing",
                    LocalDateTime.of(2021, 01, 01, 10, 12, 24),
                    UserDTOConverter.UserToGetUserDTO(dm1),
                    thread1
            );

            Publication publication3 = new Publication("I have a lot of characters created but I don't know how to play them",
                    LocalDateTime.of(2021, 01, 01, 10, 11, 24),
                    UserDTOConverter.UserToGetUserDTO(player1),
                    thread2
            );

            Publication publication4 = new Publication("What a shame there aren't good Dms atm...",
                    LocalDateTime.of(2021, 01, 01, 10, 11, 24),
                    UserDTOConverter.UserToGetUserDTO(player1),
                    thread4
            );

            Publication publication5 = new Publication("I'm Robert, I love board games, it's time to try Plug&Roll!",
                    LocalDateTime.of(2021, 01, 01, 10, 11, 24),
                    UserDTOConverter.UserToGetUserDTO(master),
                    thread5
            );

            Publication publication6 = new Publication("You can see this if you're admin or DM",
                    LocalDateTime.of(2021, 01, 01, 10, 11, 24),
                    UserDTOConverter.UserToGetUserDTO(master),
                    thread3
            );


            Publication publication7 = new Publication("You can see this if you're admin or Player",
                    LocalDateTime.of(2021, 01, 01, 10, 11, 24),
                    UserDTOConverter.UserToGetUserDTO(master),
                    thread2
            );

            Publication publication8 = new Publication("Message just for admins",
                    LocalDateTime.of(2021, 01, 01, 10, 11, 24),
                    UserDTOConverter.UserToGetUserDTO(master),
                    thread1
            );
            Publication publication9 = new Publication("Stop arguing in the forums",
                    LocalDateTime.of(2021, 01, 01, 10, 11, 24),
                    UserDTOConverter.UserToGetUserDTO(master),
                    thread2
            );
            Publication publication10 = new Publication("I like M&ms, sorry if you don't",
                    LocalDateTime.of(2021, 01, 01, 10, 11, 24),
                    UserDTOConverter.UserToGetUserDTO(player1),
                    thread1
            );
            Publication publication11 = new Publication("Is there any good player here?",
                    LocalDateTime.of(2021, 01, 01, 10, 11, 24),
                    UserDTOConverter.UserToGetUserDTO(dm1),
                    thread3
            );
            Publication publication12 = new Publication("have you seen the last Mathew Mercer game?",
                    LocalDateTime.of(2021, 01, 01, 10, 11, 24),
                    UserDTOConverter.UserToGetUserDTO(dm1),
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
                    "It is not easy to learn how to play D&D, I know this price is not easy to pay, but you won't regret",
                    CoachingType.PLAYER, 40.00,UserDTOConverter.UserToGetUserDTO(player1));
            CoachingOffer offer3 = new CoachingOffer("Coach for begginers. Includes Spells, levels, items, etc.. You will be amazed",
                    "Yoy have to learn a lot of stuff in order to start playing, I'll show you the basics of spells, levels, items.. Just follow " +
                            "what I say and you will be playing with just 1 session" ,
                    CoachingType.PLAYER, 15.00, UserDTOConverter.UserToGetUserDTO(player1));
            CoachingOffer offer4 = new CoachingOffer("Advanced Dms coaching",
                    "There are a lot of DMs who know who to guide a game for begginers, but there are not so many DMs who can guide " +
                            "a game for advanced players in a good way. I can show you the best practices",
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
