package com.junioroffers.domain.loginandregister;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findUserByUsername(String username);

    boolean existByUsername(String username);

    User save(User user);
}
