package com.epam.esm.servises;

import com.epam.esm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService extends EntityService<User> {
    @Override
    Page<User> getAllEntity(Pageable pageable);

    @Override
    void saveEntity(User user);

    @Override
    void updateEntity(long id, User user);

    @Override
    Optional<User> getEntity(long id);

    @Override
    void deleteEntity(long id);
}
