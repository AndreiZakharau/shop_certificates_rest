package com.epam.esm.repository;

import com.epam.esm.entity.User;

import java.util.Optional;

public interface UserRepository extends EntityRepository<User> {


    void deleteEntity(long id);


    Optional<User> getUserByName(String name);

    int countAllUsers();
}
