package com.epam.esm.service.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.User;
import com.epam.esm.exception.IncorrectDataException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.mapper.impl.certificateMapper.CreateCertificateFromModelCertificateMapper;
import com.epam.esm.mapper.impl.certificateMapper.ModelCertificateInOnlyCertificateMapper;
import com.epam.esm.mapper.impl.certificateMapper.OnlyCertificateReadMapper;
import com.epam.esm.mapper.impl.orderMapper.CreateOrderMapper;
import com.epam.esm.mapper.impl.userMapper.CreateUserModelMapper;
import com.epam.esm.mapper.impl.userMapper.UserModelReadMapper;
import com.epam.esm.Dto.certificateDto.ReadCertificate;
import com.epam.esm.Dto.orderDto.CreateOrder;
import com.epam.esm.Dto.userDto.CreateUser;
import com.epam.esm.Dto.userDto.ReadUser;
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

public class UserServiceImpl implements UserService<User> {

    private final UserRepositoryImpl repository;
    private final CreateUserModelMapper readMapper;
    private final UserModelReadMapper modelReadMapper;
    private final CertificateServiceImpl certificateService;
    private final CreateOrderMapper orderMapper;
    private final CreateCertificateFromModelCertificateMapper certificateMapper;
    private final OnlyCertificateReadMapper onlyCertificateReadMapper;
    private final OrderRepositoryImpl orderRepository;
    private final ModelCertificateInOnlyCertificateMapper onlyCertificateMapper;
    private final LanguageMassage languageMassage;

    @Transactional
    @Override
    public List<ReadUser> getAllEntity(int limit, int offset) {
        return readMapper.buildUserModelReadMapper(repository.getAllEntity(limit, offset));
    }

    @Transactional
    @Override
    public void saveEntity(User user) {
        if (repository.getUserByName(user.getNickName()).isEmpty()) {
            repository.addEntity(user);
        } else {
            throw new IncorrectDataException(languageMassage.getMessage("message.such.user"));
        }

    }

    @Transactional
    @Override
    public void updateEntity(long id, User user) {
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
    public Optional<ReadUser> getEntity(long id) {
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
    public int countAllUsers() {
        return repository.countAllUsers();
    }

    @Transactional
    public Optional<CreateUser> getUserByName(String name) {
        Optional<User> user = Optional.ofNullable(repository.getUserByName(name)).orElseThrow();
        return user.map(modelReadMapper::mapFrom);
    }


    @Transactional
    public CreateOrder purchaseCertificate(long userId, long certificateId) {

        Optional<ReadCertificate> certificate = certificateService.getEntity(certificateId);
        certificate.get().setCreateDate(LocalDateTime.now());
        certificateService.updateEntity(certificateId, (onlyCertificateMapper.mapFrom(certificate.get())));
        Optional<User> user = Optional.ofNullable(repository.getEntityById(userId)).orElseThrow();
        List<Certificate> list = new ArrayList<>();
        Optional<ReadCertificate> updateCertificate = certificateService.getEntity(certificateId);
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
