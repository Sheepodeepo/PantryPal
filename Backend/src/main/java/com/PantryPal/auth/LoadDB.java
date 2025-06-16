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

            log.info("Preload Recipe 1: {}", recipeRepository.save(new Recipe(u1.getId(),"dummy-name1", MealType.BREAKFAST,"dummy-ingredients1","dummy-instructions1")));
            log.info("Preload Recipe 2: {}", recipeRepository.save(new Recipe(u1.getId(),"dummy-name2", MealType.BREAKFAST,"dummy-ingredients2","dummy-instructions2")));
            log.info("Preload Recipe 3: {}", recipeRepository.save(new Recipe(u2.getId(),"dummy-name3", MealType.BREAKFAST,"dummy-ingredients3","dummy-instructions3")));
            log.info("Preload Recipe 4: {}", recipeRepository.save(new Recipe(u3.getId(),"dummy-name4", MealType.BREAKFAST,"dummy-ingredients4","dummy-instructions4")));
            log.info("Preload Recipe 5: {}", recipeRepository.save(new Recipe(u3.getId(),"dummy-name5", MealType.BREAKFAST,"dummy-ingredients5","dummy-instructions5")));
            log.info("Preload Recipe 6: {}", recipeRepository.save(new Recipe(u3.getId(),"dummy-name6", MealType.BREAKFAST,"dummy-ingredients6","dummy-instructions6")));
            log.info("Preload Recipe 7: {}", recipeRepository.save(new Recipe(u3.getId(),"Lemon Herb Roasted Chicken and Veggies", MealType.DINNER,"1 whole chicken (about 3-4 lbs), 1 lb baby potatoes (halved), 1 lb carrots (chopped), 1 large onion (quartered), 2 lemons (1 sliced, 1 juiced), 4 cloves garlic (minced), 2 tbsp olive oil, 1 tbsp dried Italian herbs, 1 tsp salt, 1/2 tsp black pepper, Fresh rosemary sprigs (optional), Fresh thyme sprigs (optional)"
                    ,"Preheat oven to 400°F (200°C), In a large bowl, toss potatoes, carrots, and onion with 1 tbsp olive oil, salt, pepper, and Italian herbs, Place the vegetables in a single layer in a large roasting pan, Rinse the chicken and pat it dry with paper towels, In a small bowl, combine lemon juice, minced garlic, remaining 1 tbsp olive oil, salt, and pepper, Rub the mixture all over the chicken, including under the skin of the breast, Stuff the chicken cavity with lemon slices, rosemary, and thyme (if using), Place the chicken on top of the vegetables in the roasting pan, Roast for 1 hour and 15 minutes, or until the internal temperature of the chicken reaches 165°F (74°C) in the thickest part of the thigh, Let the chicken rest for 10-15 minutes before carving, Serve the chicken and vegetables immediately.>")
            ));

        };
    }
}
