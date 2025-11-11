package be.pxl.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * OrganizationServiceApplication
 *
 */

@SpringBootApplication
public class OrganizationServiceApplication {

    public static void main( String[] args )
    {
        SpringApplication.run(OrganizationServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
