package com.epam.esm.mapper.impl.userMapper;

import com.epam.esm.entitys.User;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.models.users.ReadUserModel;
import org.springframework.stereotype.Service;

@Service
public class UserModelReadMapper implements Mapper<User, ReadUserModel> {

    @Override
    public ReadUserModel mapFrom(User object) {
        return new ReadUserModel(
                object.getId(),
                object.getNickName()
        );
    }

//    public List<ReadUserModel> buildReadUserModel(List<User> users){
//        List<ReadUserModel> list = new ArrayList<>();
//        for (User user : users){
//            ReadUserModel readUserModel = ReadUserModel.builder()
//                    .id(user.getId())
//                    .nickName(user.getNickName())
//                    .build();
//                    new ReadUserModel();
//            readUserModel.setId(user.getId());
//            readUserModel.setNickName(user.getNickName());
//            list.add(readUserModel);
//        }
//        return list;
//    }
}
