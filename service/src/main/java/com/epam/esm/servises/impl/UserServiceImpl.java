package com.epam.esm.servises.impl;

import com.epam.esm.entitys.Certificate;
import com.epam.esm.entitys.User;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.mapper.impl.certificateMapper.CreateCertificateFromModelCertificateMapper;
import com.epam.esm.mapper.impl.certificateMapper.ModelCertificateReadMapper;
import com.epam.esm.mapper.impl.certificateMapper.OnlyCertificateReadMapper;
import com.epam.esm.mapper.impl.orderMapper.CreateOrderMapper;
import com.epam.esm.mapper.impl.userMapper.CreateUserModelMapper;
import com.epam.esm.mapper.impl.userMapper.UserModelReadMapper;
import com.epam.esm.models.certificates.ModelCertificate;
import com.epam.esm.models.orders.CreateOrderModel;
import com.epam.esm.models.users.ReadUserModel;
import com.epam.esm.models.users.UserModel;
import com.epam.esm.repositorys.impl.OrderRepositoryImpl;
import com.epam.esm.repositorys.impl.UserRepositoryImpl;
import com.epam.esm.servises.UserService;
import com.epam.esm.util.impl.CertificateValidator;
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
    private final ModelCertificateReadMapper modelCertificateReadMapper;
    private final OnlyCertificateReadMapper onlyCertificateReadMapper;
    private final OrderRepositoryImpl orderRepository;
    private final CertificateValidator certificateValidator;

    @Transactional
    @Override
    public List<UserModel> getAllEntity(int limit, int offset) {
        return readMapper.buildUserModelReadMapper(repository.getAllEntity(limit, offset));
    }

    @Transactional
    @Override
    public void saveEntity(User user) {
        if(repository.getUserByName(user.getNickName()).isEmpty()){
            repository.addEntity(user);
        }else {
            //TODO return такой пользователь уже существует
        }

    }

    @Transactional
    @Override
    public void updateEntity(long id, User user) {
        Optional<User> user1 = repository.getEntityById(id);
        if(user1.isPresent()) {
            repository.addEntity(user);
        }else {
            //TODO пользователь с таким id не найден
        }
    }

    @Transactional
    @Override
    public Optional<UserModel> getEntity(long id) {
        Optional<User> user = Optional.ofNullable(repository.getEntityById(id)).orElseThrow();
        if(user.isEmpty()) {
            throw new NoSuchEntityException("message.user.with.id");
        }
        return user.map(readMapper::mapFrom);
    }

    @Transactional
    @Override
    public void deleteEntity(long id) {
        Optional<User> user = repository.getEntityById(id);
        if(user.isEmpty()) {
            //TODO пользователь с таким id не найден
        }else {
            repository.deleteEntity(id);
        }
    }

    @Transactional
    public int countAllUsers() {
        return repository.countAllUsers();
    }

    @Transactional
    public Optional<ReadUserModel> getUserByName(String name) {
        Optional<User> user = Optional.ofNullable(repository.getUserByName(name)).orElseThrow();
        return user.map(modelReadMapper::mapFrom);
    }



    @Transactional
    public CreateOrderModel purchaseCertificate(long userId, long certificateId) {

        Optional<Certificate> certificate = certificateValidator.isPresentCertificate(certificateId);
        ModelCertificate modelCertificate = modelCertificateReadMapper.mapFrom(certificate.orElseThrow());
        modelCertificate.setCreateDate(LocalDateTime.now());
        certificateService.updateEntity(certificateId,modelCertificate);
        Optional<User> user = Optional.ofNullable(repository.getEntityById(userId)).orElseThrow();
        List<Certificate> list = new ArrayList<>();
        Certificate updateCertificate = certificateValidator.isPresentCertificate(certificateId).orElseThrow();
        list.add(updateCertificate);
        CreateOrderModel model = CreateOrderModel.builder()
                .user(user.map(modelReadMapper::mapFrom).orElseThrow())
                .certificate(onlyCertificateReadMapper.buildListCertificates(list)).build();
            model.setUser((user.map(modelReadMapper::mapFrom).orElseThrow()));
            model.setCertificate(onlyCertificateReadMapper.buildListCertificates(list));
            model.setCost(updateCertificate.getPrice());
            model.setDatePurchase(LocalDateTime.now());
            orderRepository.saveOrder(orderMapper.mapFrom(model));
//        CreateOrderModel model = new CreateOrderModel();
//        if (certificateValidator.isPresentCertificate(certificateId))
//        Optional<ModelCertificate> modelCertificate =
//                Optional.ofNullable(certificateService.getEntity(certificateId)).orElseThrow();
//        Certificate certificate = modelCertificate.map(certificateMapper::mapFrom).orElseThrow();
//        certificate = certificateService.updateEntity(certificateId,modelCertificate);
//        List<Certificate> list = new ArrayList<>();
//        list.add(certificate);
//        Optional<User> user = Optional.ofNullable(repository.getEntityById(userId)).orElseThrow();
//        if(modelCertificate.isEmpty()) {
//            throw new NoSuchEntityException("такой сертификат не найден");
//        }else {
//            repository.addCertificate(certificate);
//            List<Certificate> list = new ArrayList<>();
//            list.add(certificate);
//        CreateOrderModel model = CreateOrderModel.builder()
//                .user(user.map(modelReadMapper::mapFrom).orElseThrow())
//                .certificate(onlyCertificateReadMapper.buildListCertificates(list)).build();
//            model.setUser((user.map(modelReadMapper::mapFrom).orElseThrow()));
//            model.setCertificate(onlyCertificateReadMapper.buildListCertificates(list));
//            model.setCost(certificate.getPrice());
//            model.setDatePurchase(LocalDateTime.now());
//            Order order = orderRepository.saveOrder(orderMapper.mapFrom(model));
//            orderRepository.saveOrderCertificates(order.getId(),certificate.getId());
//        }
        return model;
    }
}
