package com.PantryPal.repository;


import com.PantryPal.model.User;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<User, Long> {
/*    void findUserMyEmail(String email);
    void deleteUserByEmail(String email);*/

}
