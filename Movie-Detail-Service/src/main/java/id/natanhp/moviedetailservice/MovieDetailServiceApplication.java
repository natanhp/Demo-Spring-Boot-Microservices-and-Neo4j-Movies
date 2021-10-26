package id.natanhp.moviedetailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MovieDetailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieDetailServiceApplication.class, args);
    }

}
