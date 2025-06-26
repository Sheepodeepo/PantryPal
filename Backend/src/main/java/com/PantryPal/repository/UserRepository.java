package com.PantryPal.repository;


import com.PantryPal.model.Recipe;
import com.PantryPal.model.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends ListCrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);

}
