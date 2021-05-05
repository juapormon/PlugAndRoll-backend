package com.plugandroll.version1.config;

import com.plugandroll.version1.models.Prueba;
import com.plugandroll.version1.models.TypeRol;
import com.plugandroll.version1.models.UserEntity;
import com.plugandroll.version1.repositories.PruebaRepository;
import com.plugandroll.version1.repositories.UserEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class MongoDBPopulate<E> {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Bean
    CommandLineRunner commandLineRunner(UserEntityRepository userEntityRepository, PruebaRepository pruebaRepository) {
        return strings -> {
            userEntityRepository.deleteAll();
            pruebaRepository.deleteAll();

            /*================= USERS =================*/

            UserEntity master = new UserEntity("master",
                    passwordEncoder.encode("master"),
                    "master@masterchef.com",
                    true,
                    Stream.of(TypeRol.ADMIN, TypeRol.DM).collect(Collectors.toSet()));

            userEntityRepository.save(master);

            /*================= PRUEBAS =================*/

            Prueba prueba = new Prueba(new String(Files.readAllBytes(Paths.get("C:/Users/juanc/Escritorio/message.txt"))));

            pruebaRepository.save(prueba);

        };
    }
}
