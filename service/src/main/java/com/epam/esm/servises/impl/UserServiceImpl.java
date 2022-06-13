package com.epam.esm.servises.impl;

import com.epam.esm.entity.User;
import com.epam.esm.repositorys.impl.UserRepositoryImpl;
import com.epam.esm.servises.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("UserService")
@Transactional
public class UserServiceImpl implements EntityService <User> {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Override
    public List<User> getAllEntity() {
        return userRepositoryImpl.getAllEntity();
    }

    @Override
    public void saveEntity(User user) {
        if(userRepositoryImpl.getUserByName(user.getNickName()).isEmpty()){
            userRepositoryImpl.addEntity(user);
        }else {
            //TODO return такой пользователь уже существует
        }

    }

    @Override
    public void updateEntity(long id, User user) {
        Optional<User> user1 = userRepositoryImpl.getEntity(id);
        if(user1.isPresent()) {
            userRepositoryImpl.addEntity(user);
        }else {
            //TODO пользователь с таким id не найден
        }
    }

    @Override
    public Optional<User> getEntity(long id) {
        Optional<User> user = userRepositoryImpl.getEntity(id);
        if(user.isEmpty()) {
            //TODO пользователь с таким id не найден
        }
        return user;
    }

    @Override
    public void deleteEntity(long id) {
        Optional<User> user = userRepositoryImpl.getEntity(id);
        if(user.isEmpty()) {
            //TODO пользователь с таким id не найден
        }else {
            userRepositoryImpl.deleteEntity(id);
        }
    }

    public Optional<User> getUserByName(String name){
        return userRepositoryImpl.getUserByName(name);
    }
}
