package com.epam.esm.service.impl;

import com.epam.esm.Dto.orderDto.CreateOrder;
import com.epam.esm.Dto.userDto.CreateUser;
import com.epam.esm.Dto.userDto.ReadUser;
import com.epam.esm.Dto.userDto.UserDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.User;
import com.epam.esm.exception.IncorrectDataException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateDtoFromCertificate;
import com.epam.esm.mapper.impl.orderMapper.TransitionOrderFromCreateOrder;
import com.epam.esm.mapper.impl.userMapper.TransitionReadUserFromUser;
import com.epam.esm.mapper.impl.userMapper.TransitionUserDtoFromUser;
import com.epam.esm.mapper.impl.userMapper.TransitionUserFromCreateUser;
import com.epam.esm.mapper.impl.userMapper.TransitionUserFromUserDto;
import com.epam.esm.repository.impl.CertificateRepositoryImpl;
import com.epam.esm.repository.impl.OrderRepositoryImpl;
import com.epam.esm.repository.impl.UserRepositoryImpl;
import com.epam.esm.service.UserService;
import com.epam.esm.util.messange.LanguageMassage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("UserService")
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepositoryImpl repository;
    private final OrderRepositoryImpl orderRepository;
    private final CertificateRepositoryImpl certificateRepository;

    private final TransitionReadUserFromUser readMapper;
    private final TransitionOrderFromCreateOrder createOrder;
    private final TransitionUserFromUserDto userFromUserDto;
    private final TransitionUserDtoFromUser userDtoFromUser;
    private final TransitionUserFromCreateUser userFromCreateUser;
    private final TransitionCertificateDtoFromCertificate certificateDtoFromCertificate;

    private final LanguageMassage languageMassage;

    @Transactional
    @Override
    public List<ReadUser> getAllEntity(int limit, int offset) {
        return readMapper.buildUserModelReadMapper(repository.getAllEntity(limit, offset));
    }

    @Transactional
    @Override
    public void saveEntity(CreateUser createUser) {
        User user = userFromCreateUser.mapFrom(createUser);
        if (repository.getUserByName(user.getNickName()).isEmpty()) {
            repository.addEntity(user);
        } else {
            throw new IncorrectDataException(languageMassage.getMessage("message.such.user"));
        }

    }

    @Transactional
    @Override
    public void updateEntity(long id, UserDto userDto) {
        User user = userFromUserDto.mapFrom(userDto);
        Optional<User> user1 = repository.getEntityById(id);
        if (user1.isPresent()) {
            user.setId(id);
            if(user.getNickName()==null)
                user.setNickName(user1.get().getNickName());
            if(user.getEmail()==null)
                user.setEmail(user1.get().getEmail());
            repository.updateEntity(user);
        } else {
            throw new NoSuchEntityException(languageMassage.getMessage("message.user.with.id"));
        }
    }

    @Transactional
    @Override
    public Optional<ReadUser> findById(long id) {
        Optional<User> user = Optional.ofNullable(repository.getEntityById(id)).orElseThrow();
        if (user.isEmpty()) {
            throw new NoSuchEntityException(languageMassage.getMessage("message.user.with.id"));
        }
        return user.map(readMapper::mapFrom);
    }

    @Transactional
    @Override
    public void deleteEntity(long id) {
        Optional<User> user = repository.getEntityById(id);
        if (user.isEmpty()) {
            throw new NoSuchEntityException(languageMassage.getMessage("message.user.with.id"));
        } else {
            repository.deleteEntity(id);
        }
    }

    @Transactional
    @Override
    public int countAll() {
        return repository.countAllUsers();
    }

    @Transactional
    @Override
    public Optional<ReadUser> getUserByName(String name) {
        Optional<User> user = Optional.ofNullable(repository.getUserByName(name)).orElseThrow();
        return user.map(readMapper::mapFrom);
    }


    @Transactional
    @Override
    public CreateOrder purchaseCertificate(long userId, long certificateId) {

        Optional<Certificate> certificate = certificateRepository.getEntityById(certificateId);
        Optional<User> user = repository.getEntityById(userId);
        CreateOrder order = new CreateOrder();
        if(certificate.isPresent()&&user.isPresent()){
            order.setUser(userDtoFromUser.mapFrom(user.orElseThrow()));
            order.setCertificate(certificateDtoFromCertificate.mapFrom(certificate.orElseThrow()));
            order.setCost(certificate.get().getPrice());
            order.setDatePurchase(LocalDateTime.now());
            orderRepository.addEntity(createOrder.mapFrom(order));
        }

        return order;
    }
}
