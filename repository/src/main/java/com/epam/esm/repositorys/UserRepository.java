package com.epam.esm.repositorys;

import com.epam.esm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository extends EntityRepository<User> {
    @Override
    Page<User> getAllEntity(Pageable pageable);

    @Override
    Optional<User> getEntity(long id);

    @Override
    void addEntity(User user);

    @Override
    void deleteEntity(long id);

    Optional<User> getUserByName(String name);
}
