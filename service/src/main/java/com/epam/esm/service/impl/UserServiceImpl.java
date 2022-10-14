package com.epam.esm.service.impl;

import com.epam.esm.Dto.certificateDto.ReadCertificate;
import com.epam.esm.Dto.orderDto.CreateOrder;
import com.epam.esm.Dto.userDto.CreateUser;
import com.epam.esm.Dto.userDto.ReadUser;
import com.epam.esm.Dto.userDto.UserDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.User;
import com.epam.esm.exception.IncorrectDataException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateFromReadCertificate;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCreateCertificateFromCertificate;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCreateCertificateInFromReadCertificate;
import com.epam.esm.mapper.impl.orderMapper.TransitionOrderFromCreateOrder;
import com.epam.esm.mapper.impl.userMapper.TransitionCreateUserFromUser;
import com.epam.esm.mapper.impl.userMapper.TransitionReadUserFromUser;
import com.epam.esm.mapper.impl.userMapper.TransitionUserFromCreateUser;
import com.epam.esm.mapper.impl.userMapper.TransitionUserFromReadUser;
import com.epam.esm.mapper.impl.userMapper.TransitionUserFromUserDto;
import com.epam.esm.repository.impl.OrderRepositoryImpl;
import com.epam.esm.repository.impl.UserRepositoryImpl;
import com.epam.esm.service.UserService;
import com.epam.esm.util.messange.LanguageMassage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("UserService")
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepositoryImpl repository;
    private final OrderRepositoryImpl orderRepository;
    private final CertificateServiceImpl certificateService;

    private final TransitionReadUserFromUser readMapper;
    private final TransitionUserFromUserDto userFromUserDto;
    private final TransitionCreateUserFromUser modelReadMapper;
    private final TransitionUserFromCreateUser userFromCreateUser;
    private final TransitionUserFromReadUser userFromReadUser;
    private final TransitionOrderFromCreateOrder orderMapper;
    private final TransitionCertificateFromReadCertificate certificateMapper;
    private final TransitionCreateCertificateFromCertificate onlyCertificateReadMapper;
    private final TransitionCreateCertificateInFromReadCertificate onlyCertificateMapper;

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


    @Transactional  // TODO logic save order
    public CreateOrder purchaseCertificate(long userId, long certificateId) {

        Optional<ReadCertificate> certificate = certificateService.findById(certificateId);
//        certificateService.updateEntity(certificateId, (onlyCertificateMapper.mapFrom(certificate.get())));
        Optional<User> user = Optional.ofNullable(repository.getEntityById(userId)).orElseThrow();
        List<Certificate> list = new ArrayList<>();
        Optional<ReadCertificate> updateCertificate = certificateService.findById(certificateId);
        list.add(certificateMapper.mapFrom(updateCertificate.get()));
        CreateOrder model = CreateOrder.builder()
                .user(user.map(modelReadMapper::mapFrom).orElseThrow())
                .certificate(onlyCertificateReadMapper.buildListCertificates(list)).build();
        model.setUser((user.map(modelReadMapper::mapFrom).orElseThrow()));
        model.setCertificate(onlyCertificateReadMapper.buildListCertificates(list));
        model.setCost(updateCertificate.get().getPrice());
        model.setDatePurchase(LocalDateTime.now());
        orderRepository.saveOrder(orderMapper.mapFrom(model));

        return model;
    }
}
