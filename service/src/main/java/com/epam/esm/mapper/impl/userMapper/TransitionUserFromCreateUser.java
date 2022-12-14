package com.epam.esm.mapper.impl.userMapper;

import com.epam.esm.entity.User;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.Dto.userDto.CreateUser;
import org.springframework.stereotype.Service;

@Service
public class TransitionUserFromCreateUser implements Mapper<CreateUser, User> {
    @Override
    public User mapFrom(CreateUser object) {
        return User.builder()
                .nickName(object.getNickName())
                .email(object.getEmail())
                .build();
    }
}
