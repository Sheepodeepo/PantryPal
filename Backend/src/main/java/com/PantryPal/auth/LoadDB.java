package com.PantryPal.auth;

import com.PantryPal.model.MealType;
import com.PantryPal.model.Recipe;
import com.PantryPal.model.User;
import com.PantryPal.repository.RecipeRepository;
import com.PantryPal.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("!test")
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
            User u1 = userRepository.save(new User("test", passwordEncoder.encode("test")));
            User u2 = userRepository.save(new User("test@gmail", passwordEncoder.encode("longpasswordtest1")));
            User u3 = userRepository.save(new User("test@test", passwordEncoder.encode("test")));
            log.info("Preload User 1: {}", u1);
            log.info("Preload User 2: {}", u2);
            log.info("Preload User 3: {}", u3);

            log.info("Preload Recipe 1: {}", recipeRepository.save(new Recipe(u1.getId(),"dummy-name", MealType.BREAKFAST,"dummy-ingredients","dummy-instructions")));
            log.info("Preload Recipe 2: {}", recipeRepository.save(new Recipe(u1.getId(),"dummy-name2", MealType.BREAKFAST,"dummy-ingredients2","dummy-instructions2")));
            log.info("Preload Recipe 3: {}", recipeRepository.save(new Recipe(u2.getId(),"dummy-name3", MealType.BREAKFAST,"dummy-ingredients3","dummy-instructions3")));
            log.info("Preload Recipe 4: {}", recipeRepository.save(new Recipe(u3.getId(),"dummy-name4", MealType.BREAKFAST,"dummy-ingredients4","dummy-instructions4")));
            log.info("Preload Recipe 5: {}", recipeRepository.save(new Recipe(u3.getId(),"dummy-name5", MealType.BREAKFAST,"dummy-ingredients5","dummy-instructions5")));
            log.info("Preload Recipe 6: {}", recipeRepository.save(new Recipe(u3.getId(),"dummy-name6", MealType.BREAKFAST,"dummy-ingredients6","dummy-instructions6")));

        };
    }
}
