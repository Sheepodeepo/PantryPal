package com.PantryPal.auth;

import com.PantryPal.model.User;
import com.PantryPal.repository.RecipeRepository;
import com.PantryPal.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class LoadDB {
    private static final Logger log = LoggerFactory.getLogger(LoadDB.class);
    private final AuthConfig authConfig;

    public LoadDB(AuthConfig authConfig) {
        this.authConfig = authConfig;
    }

    @Bean
    CommandLineRunner preLoadDB(RecipeRepository recipeRepository, UserRepository userRepository){
        return args -> {
            PasswordEncoder passwordEncoder = authConfig.passwordEncoder();
            log.info("Preload User 1: {}", userRepository.save(new User("test", passwordEncoder.encode("test"))));
            log.info("Preload User 2: {}", userRepository.save(new User("test@gmail", passwordEncoder.encode("longpasswordtest1"))));

        };
    }
}
