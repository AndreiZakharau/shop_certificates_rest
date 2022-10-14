package com.epam.esm.repository;

import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends EntityRepository<User> {
    @Override
    List<User> getAllEntity(int limit, int offset);

    @Override
    Optional<User> getEntityById(long id);

    @Override
    void addEntity(User user);

    void deleteEntity(long id);

    @Override
    void updateEntity(User user);

    Optional<User> getUserByName(String name);

    int countAllUsers();
}
