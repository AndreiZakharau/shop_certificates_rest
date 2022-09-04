package com.epam.esm.servises;

import java.util.List;
import java.util.Optional;

public interface UserService <T> {
    List<T> getAllEntity(int limit, int offset);

    void saveEntity(T t);

    void updateEntity(long id, T t);

    Optional<T> getEntity(long id);

    void deleteEntity(long id);
}
