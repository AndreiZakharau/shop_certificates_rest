package com.epam.esm.servises;

import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends EntityService<User> {
    @Override
    List<User> getAllEntity();

    @Override
    void saveEntity(User user);

    @Override
    void updateEntity(long id, User user);

    @Override
    Optional<User> getEntity(long id);

    @Override
    void deleteEntity(long id);
}
