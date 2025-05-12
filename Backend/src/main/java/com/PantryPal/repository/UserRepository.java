package com.PantryPal.repository;


import com.PantryPal.model.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserRepository extends ListCrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);

}
