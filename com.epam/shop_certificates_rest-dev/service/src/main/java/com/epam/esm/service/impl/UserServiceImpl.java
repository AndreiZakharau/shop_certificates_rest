//package com.epam.esm.service.impl;
//
//import com.epam.esm.Dto.orderDto.CreateOrder;
//import com.epam.esm.Dto.userDto.CreateUser;
//import com.epam.esm.Dto.userDto.ReadUser;
//import com.epam.esm.Dto.userDto.UserDto;
//import com.epam.esm.entity.Certificate;
//import com.epam.esm.entity.User;
//import com.epam.esm.exception.IncorrectDataException;
//import com.epam.esm.exception.NoSuchEntityException;
//import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateDtoFromCertificate;
//import com.epam.esm.mapper.impl.orderMapper.TransitionOrderFromCreateOrder;
//import com.epam.esm.mapper.impl.userMapper.TransitionReadUserFromUser;
//import com.epam.esm.mapper.impl.userMapper.TransitionUserDtoFromUser;
//import com.epam.esm.mapper.impl.userMapper.TransitionUserFromCreateUser;
//import com.epam.esm.mapper.impl.userMapper.TransitionUserFromUserDto;
//
//import com.epam.esm.repository.CertificateRepository;
//import com.epam.esm.repository.OrderRepository;
//import com.epam.esm.repository.UserRepository;
//import com.epam.esm.service.UserService;
//import com.epam.esm.util.messange.LanguageMassage;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@Service("UserService")
//@RequiredArgsConstructor
//
//public class UserServiceImpl implements UserService {
//
//    private final UserRepository repository;
//    private final OrderRepository orderRepository;
//    private final CertificateRepository certificateRepository;
//
//    private final TransitionReadUserFromUser readMapper;
//    private final TransitionOrderFromCreateOrder createOrder;
//    private final TransitionUserFromUserDto userFromUserDto;
//    private final TransitionUserDtoFromUser userDtoFromUser;
//    private final TransitionUserFromCreateUser userFromCreateUser;
//    private final TransitionCertificateDtoFromCertificate certificateDtoFromCertificate;
//
//    private final LanguageMassage languageMassage;
//
//    @Transactional
//    @Override
//    public List<ReadUser> getAllEntity(int limit, int offset) {
//        return readMapper.buildUserModelReadMapper(repository.findAll(limit, offset));
//    }
//
//    @Transactional
//    @Override
//    public void saveEntity(CreateUser createUser) {
//        User user = userFromCreateUser.mapFrom(createUser);
//        if (repository.findUserByNickName(user.getNickName())==null) {
//            repository.save(user);
//        } else {
//            throw new IncorrectDataException(languageMassage.getMessage("message.such.user"));
//        }
//
//    }
//
//    @Transactional
//    @Override
//    public void updateEntity(long id, UserDto userDto) {
//        User user = userFromUserDto.mapFrom(userDto);
//        User user1 = repository.getReferenceById(id);
//        if (user1==null) {
//            user.setId(id);
//            if(user.getNickName()==null)
//                user.setNickName(user1.getNickName());
//            if(user.getEmail()==null)
//                user.setEmail(user1.getEmail());
//            repository.save(user);
//        } else {
//            throw new NoSuchEntityException(languageMassage.getMessage("message.user.with.id"));
//        }
//    }
//
//    @Transactional
//    @Override
//    public ReadUser findById(long id) {
//        User user = repository.getReferenceById(id);
//        if (user == null) {
//            throw new NoSuchEntityException(languageMassage.getMessage("message.user.with.id"));
//        }
//        return readMapper.mapFrom(user);
//    }
//
//    @Transactional
//    @Override
//    public void deleteEntity(long id) {
//        User user = repository.getReferenceById(id);
//        if (user==null) {
//            throw new NoSuchEntityException(languageMassage.getMessage("message.user.with.id"));
//        } else {
//            repository.deleteById(id);
//        }
//    }
//
//    @Transactional
//    @Override
//    public Long countAll() {
//        return repository.count();
//    }
//
//    @Transactional
//    @Override
//    public ReadUser getUserByName(String name) {
//        User user = repository.findUserByNickName(name);
//        return readMapper.mapFrom(user);
//    }
//
//
//    @Transactional
//    @Override
//    public CreateOrder purchaseCertificate(long userId, long certificateId) {
//
//        Optional<Certificate> certificate = certificateRepository.findById(certificateId);
//        Optional<User> user = repository.findById(userId);
//        CreateOrder order = new CreateOrder();
//        if(certificate.isPresent()&&user.isPresent()){
//            order.setUser(userDtoFromUser.mapFrom(user.orElseThrow()));
//            order.setCertificate(certificateDtoFromCertificate.mapFrom(certificate.orElseThrow()));
//            order.setCost(certificate.get().getPrice());
//            order.setDatePurchase(LocalDateTime.now());
//            orderRepository.save(createOrder.mapFrom(order));
//        }
//
//        return order;
//    }
//}
