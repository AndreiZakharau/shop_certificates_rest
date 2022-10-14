package com.epam.esm.service;

import com.epam.esm.Dto.userDto.CreateUser;
import com.epam.esm.Dto.userDto.ReadUser;
import com.epam.esm.Dto.userDto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService extends EntityService<ReadUser, CreateUser, UserDto> {

    @Override
    List<ReadUser> getAllEntity(int limit, int offset);

    @Override
    void saveEntity(CreateUser createUser);

    @Override
    void updateEntity(long id, UserDto userDto);

    @Override
    Optional<ReadUser> findById(long id);

    @Override
    void deleteEntity(long id);

    @Override
    int countAll();

    Optional<ReadUser> getUserByName(String name);
}
