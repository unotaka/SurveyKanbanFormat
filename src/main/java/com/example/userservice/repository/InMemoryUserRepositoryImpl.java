package com.example.userservice.repository;

import com.example.userservice.model.User;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * An in-memory implementation of the UserRepository for demonstration and testing purposes.
 * Stores users in ConcurrentHashMaps.
 * This implementation is NOT thread-safe for complex update scenarios
 * and does not enforce transactional behavior like a real database.
 */
public class InMemoryUserRepositoryImpl implements UserRepository {
    // Stores users, mapping ID to User object
    private final Map<String, User> usersById = new ConcurrentHashMap<>();
    // Stores usernames, mapping username to User ID for quick uniqueness checks and lookup
    private final Map<String, String> usernamesToIds = new ConcurrentHashMap<>();
    // Stores emails, mapping email to User ID for quick uniqueness checks and lookup
    private final Map<String, String> emailsToIds = new ConcurrentHashMap<>();

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            // New user, generate ID
            String newId = UUID.randomUUID().toString();
            user.setId(newId);

            // Add to maps
            usersById.put(newId, user);
            usernamesToIds.put(user.getUsername(), newId);
            emailsToIds.put(user.getEmail(), newId);
        } else {
            // Existing user (update)
            // Retrieve the old user to check for changes in unique fields
            User existingUser = usersById.get(user.getId());

            if (existingUser != null) {
                // If username changed, remove the old username entry
                if (!existingUser.getUsername().equals(user.getUsername())) {
                    usernamesToIds.remove(existingUser.getUsername());
                }
                // If email changed, remove the old email entry
                if (!existingUser.getEmail().equals(user.getEmail())) {
                    emailsToIds.remove(existingUser.getEmail());
                }
            }
            // Put the updated user and update unique field maps
            usersById.put(user.getId(), user);
            usernamesToIds.put(user.getUsername(), user.getId());
            emailsToIds.put(user.getEmail(), user.getId());
        }
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(usersById.get(id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String id = usernamesToIds.get(username);
        return findById(id); // Use findById to retrieve the full User object
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String id = emailsToIds.get(email);
        return findById(id); // Use findById to retrieve the full User object
    }
}
