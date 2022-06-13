package com.epam.esm.repositorys;

import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends EntityRepository<User> {
    @Override
    List<User> getAllEntity();

    @Override
    Optional<User> getEntity(long id);

    @Override
    void addEntity(User user);

    @Override
    void deleteEntity(long id);

    Optional<User> getUserByName(String name);
}
