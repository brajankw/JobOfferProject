package com.junioroffers.domain.loginandregister;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepositoryTestImpl implements UserRepository {

    private final Map<String, User> userMap = new ConcurrentHashMap<>();

    @Override
    public Optional<User> findUserByUsername(String username) {
        return Optional.ofNullable(userMap.get(username));
    }

    @Override
    public boolean existByUsername(String username) {
        return userMap.containsKey(username);
    }

    @Override
    public User save(User entity) {
        UUID id = UUID.randomUUID();
        User user = new User(
                id.toString(),
                entity.username(),
                entity.password());
        userMap.put(user.username(), user);
        return user;
    }
}
