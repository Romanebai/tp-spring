package fr.diginamic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@Profile("test")
public class VilleTest {
    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "test");
                SpringApplication.run(VilleTest.class, args);
    }
}