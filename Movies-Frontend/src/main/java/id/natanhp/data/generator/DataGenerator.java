package id.natanhp.data.generator;

import com.vaadin.flow.spring.annotation.SpringComponent;

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
    public CommandLineRunner loadData(MoviesRepository moviesRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (moviesRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            logger.info("... generating 100 Movies entities...");
            ExampleDataGenerator<Movies> moviesRepositoryGenerator = new ExampleDataGenerator<>(Movies.class,
                    LocalDateTime.of(2021, 10, 28, 0, 0, 0));
            moviesRepositoryGenerator.setData(Movies::setId, DataType.ID);
            moviesRepositoryGenerator.setData(Movies::setTitle, DataType.FIRST_NAME);
            moviesRepositoryGenerator.setData(Movies::setReleased, DataType.WORD);
            // moviesRepository.saveAll(moviesRepositoryGenerator.create(100, seed));

            logger.info("Generated demo data");
        };
    }

}