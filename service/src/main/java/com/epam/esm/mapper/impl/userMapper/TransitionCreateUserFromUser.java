package com.epam.esm.mapper.impl.userMapper;

import com.epam.esm.entity.User;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.Dto.userDto.CreateUser;
import org.springframework.stereotype.Service;

@Service
public class TransitionCreateUserFromUser implements Mapper<User, CreateUser> {

    @Override
    public CreateUser mapFrom(User object) {
        return new CreateUser(
                object.getNickName(),
                object.getEmail()
        );
    }
}
