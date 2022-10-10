package com.epam.esm.mapper.impl.userMapper;

import com.epam.esm.entity.User;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.orderMapper.CreateReadOrderModelMapper;
import com.epam.esm.Dto.userDto.ReadUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateUserModelMapper implements Mapper<User, ReadUser> {

    //    private final OrderReadMapper orderReadMapper;
    private final CreateReadOrderModelMapper orderReadMapper;

    @Override
    public ReadUser mapFrom(User object) {
        return new ReadUser(
                object.getId(),
                object.getNickName(),
                orderReadMapper.buildReadOrderModel(object.getOrders())
//                orderReadMapper.buildOrderModel(object.getOrders())
        );
    }

    public List<ReadUser> buildUserModelReadMapper(List<User> users) {
        List<ReadUser> list = new ArrayList<>();
        for (User user : users) {
            ReadUser readUser = ReadUser.builder()
                    .id(user.getId())
                    .nickName(user.getNickName())
                    .orders(orderReadMapper.buildReadOrderModel(user.getOrders()))
                    .build();
            list.add(readUser);
        }
        return list;
    }
}
