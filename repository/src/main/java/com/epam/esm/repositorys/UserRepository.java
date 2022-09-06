package com.epam.esm.repositorys;

import com.epam.esm.entitys.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends EntityRepository<User> {
    @Override
    List<User> getAllEntity(int limit, int offset);

    @Override
    Optional<User> getEntityById(long id);

    @Override
    void addEntity(User user);

//    @Override
    void deleteEntity(long id);

    Optional<User> getUserByName(String name);
}
