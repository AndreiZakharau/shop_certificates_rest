package com.epam.esm.service;

import com.epam.esm.Dto.userDto.ReadUser;

import java.util.List;
import java.util.Optional;

public interface UserService<T> {
    List<ReadUser> getAllEntity(int limit, int offset);

    void saveEntity(T t);

    void updateEntity(long id, T t);

    Optional<ReadUser> getEntity(long id);

    void deleteEntity(long id);
}
