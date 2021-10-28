package id.natanhp.data.generator;

import com.vaadin.flow.spring.annotation.SpringComponent;

import id.natanhp.data.service.UserRepository;
import id.natanhp.data.entity.User;
import java.util.Collections;
import org.springframework.security.crypto.password.PasswordEncoder;
import id.natanhp.data.Role;
import id.natanhp.data.service.MoviesRepository;
import id.natanhp.data.entity.Movies;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(PasswordEncoder passwordEncoder, UserRepository userRepository,
            MoviesRepository moviesRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (userRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            logger.info("... generating 2 User entities...");
            User user = new User();
            user.setName("John Normal");
            user.setUsername("user");
            user.setHashedPassword(passwordEncoder.encode("user"));
            user.setProfilePictureUrl(
                    "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
            user.setRoles(Collections.singleton(Role.USER));
            userRepository.save(user);
            User admin = new User();
            admin.setName("John Normal");
            admin.setUsername("admin");
            admin.setHashedPassword(passwordEncoder.encode("admin"));
            admin.setProfilePictureUrl(
                    "https://images.unsplash.com/photo-1607746882042-944635dfe10e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
            admin.setRoles(Collections.singleton(Role.ADMIN));
            userRepository.save(admin);
            logger.info("... generating 100 Movies entities...");
            ExampleDataGenerator<Movies> moviesRepositoryGenerator = new ExampleDataGenerator<>(Movies.class,
                    LocalDateTime.of(2021, 10, 28, 0, 0, 0));
            moviesRepositoryGenerator.setData(Movies::setId, DataType.ID);
            moviesRepositoryGenerator.setData(Movies::setTitle, DataType.FIRST_NAME);
            moviesRepositoryGenerator.setData(Movies::setReleased, DataType.WORD);
            moviesRepository.saveAll(moviesRepositoryGenerator.create(100, seed));

            logger.info("Generated demo data");
        };
    }

}