package com.epam.esm.service;

import com.epam.esm.Dto.orderDto.CreateOrder;
import com.epam.esm.Dto.userDto.CreateUser;
import com.epam.esm.Dto.userDto.ReadUser;
import com.epam.esm.Dto.userDto.UserDto;

import java.util.List;

public interface UserService extends EntityService<ReadUser, CreateUser, UserDto> {

    @Override
    List<ReadUser> getAllEntity(int limit, int offset);

    @Override
    void saveEntity(CreateUser createUser);

    @Override
    void updateEntity(long id, UserDto userDto);

    @Override
    ReadUser findById(long id);

    @Override
    void deleteEntity(long id);

    @Override
    Long countAll();

    ReadUser getUserByName(String name);

    CreateOrder purchaseCertificate(long userId, long certificateId);
}
