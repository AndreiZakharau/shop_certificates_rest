package com.epam.esm.mapper.impl.userMapper;

import com.epam.esm.entity.User;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.model.user.ReadUserModel;
import org.springframework.stereotype.Service;

@Service
public class CreateUserFromUserModelReadMapper implements Mapper<ReadUserModel, User> {
    @Override
    public User mapFrom(ReadUserModel object) {
        return User.builder()
                .id(object.getId())
                .nickName(object.getNickName())
                .build();
    }
}
