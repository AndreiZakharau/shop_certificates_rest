package com.epam.esm.servise;

import com.epam.esm.model.user.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserService<T> {
    List<UserModel> getAllEntity(int limit, int offset);

    void saveEntity(T t);

    void updateEntity(long id, T t);

    Optional<UserModel> getEntity(long id);

    void deleteEntity(long id);
}
