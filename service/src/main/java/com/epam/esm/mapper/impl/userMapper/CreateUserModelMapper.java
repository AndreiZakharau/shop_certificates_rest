package com.epam.esm.mapper.impl.userMapper;

import com.epam.esm.entitys.User;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.orderMapper.CreateReadOrderModelMapper;
import com.epam.esm.models.users.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateUserModelMapper implements Mapper<User, UserModel> {

    private final CreateReadOrderModelMapper orderReadMapper;

    @Override
    public UserModel mapFrom(User object) {
        return new UserModel(
                object.getId(),
                object.getNickName(),
                orderReadMapper.buildReadOrderModel(object.getOrders())
        );
    }

    public List<UserModel> buildUserModelReadMapper(List<User> users) {
        List<UserModel>list = new ArrayList<>();
        for (User user : users){
            UserModel userModel = new UserModel();
            userModel.setId(user.getId());
            userModel.setNickName(user.getNickName());
            userModel.setOrders(orderReadMapper.buildReadOrderModel(user.getOrders()));
            list.add(userModel);
        }
        return list;
    }
}
