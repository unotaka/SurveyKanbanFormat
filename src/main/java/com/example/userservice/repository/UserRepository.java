package com.example.userservice.repository;

import com.example.userservice.model.User;
import java.util.Optional;

/**
 * Repository interface for User entities.
 * Defines methods for CRUD operations and querying users.
 */
public interface UserRepository {
    /**
     * Saves a user. If the user's ID is null, a new user is created and assigned an ID.
     * Otherwise, an existing user with that ID is updated.
     * @param user The user to save.
     * @return The saved user, potentially with a newly generated ID.
     */
    User save(User user);

    /**
     * Finds a user by their unique ID.
     * @param id The ID of the user.
     * @return An Optional containing the user if found, otherwise empty.
     */
    Optional<User> findById(String id);

    /**
     * Finds a user by their username.
     * @param username The username to search for.
     * @return An Optional containing the user if found, otherwise empty.
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by their email address.
     * @param email The email address to search for.
     * @return An Optional containing the user if found, otherwise empty.
     */
    Optional<User> findByEmail(String email);
}
