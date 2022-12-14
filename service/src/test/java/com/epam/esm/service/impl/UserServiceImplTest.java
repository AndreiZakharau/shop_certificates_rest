//package com.epam.esm.service.impl;
//
//import com.epam.esm.entity.Certificate;
//import com.epam.esm.entity.Order;
//import com.epam.esm.entity.User;
//import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateFromReadCertificate;
//import com.epam.esm.mapper.impl.certificateMapper.TransitionCreateCertificateInFromReadCertificate;
//import com.epam.esm.mapper.impl.certificateMapper.TransitionCreateCertificateFromCertificate;
//import com.epam.esm.mapper.impl.orderMapper.TransitionOrderFromCreateOrder;
//import com.epam.esm.mapper.impl.userMapper.TransitionReadUserFromUser;
//import com.epam.esm.mapper.impl.userMapper.TransitionCreateUserFromUser;
//import com.epam.esm.Dto.userDto.ReadUser;
//import com.epam.esm.repository.impl.OrderRepositoryImpl;
//import com.epam.esm.repository.impl.UserRepositoryImpl;
//import com.epam.esm.util.messange.LanguageMassage;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@RequiredArgsConstructor
//class UserServiceImplTest {
//
//    private UserServiceImpl userService;
//    private final UserRepositoryImpl mockRepository = Mockito.mock(UserRepositoryImpl.class);
//    private final TransitionReadUserFromUser mockCreateUserModelMapper = Mockito.mock(TransitionReadUserFromUser.class);
//    private final TransitionCreateUserFromUser mockModelReadMapper = Mockito.mock(TransitionCreateUserFromUser.class);
//    private final CertificateServiceImpl mockCertificateService = Mockito.mock(CertificateServiceImpl.class);
//    private final TransitionOrderFromCreateOrder mockCreateOrderMapper = Mockito.mock(TransitionOrderFromCreateOrder.class);
//    private final TransitionCertificateFromReadCertificate mockCreateCertificateMapper =
//            Mockito.mock(TransitionCertificateFromReadCertificate.class);
//    private final TransitionCreateCertificateFromCertificate mockOnlyCertificateReadMapper =
//            Mockito.mock(TransitionCreateCertificateFromCertificate.class);
//    private final OrderRepositoryImpl mockOrderRepository = Mockito.mock(OrderRepositoryImpl.class);
//    private final TransitionCreateCertificateInFromReadCertificate mockOnlyCertificateMapper =
//            Mockito.mock(TransitionCreateCertificateInFromReadCertificate.class);
//    private final LanguageMassage languageMassage = Mockito.mock(LanguageMassage.class);
//
//    private static final User USER_1 = User.builder()
//            .id(3L)
//            .nickName("Andrei")
//            .email("andrei666@gmail.com")
//            .build();
//    private static final User USER_2 = User.builder()
//            .id(4L)
//            .nickName("Svetlana")
//            .email("svetic_7@gmail.com")
//            .build();
//
//    private static final Certificate CERTIFICATE_1 = Certificate.builder()
//            .id(1L)
//            .certificateName("coffee")
//            .description("operates in a chain of cafes 'My Coffee'")
//            .price(1.5)
//            .duration(30)
//            .createDate(LocalDateTime.now())
//            .lastUpdateDate(LocalDateTime.now().plusDays(30))
//            .build();
//
//    private static final Order ORDER = Order.builder()
//            .id(2L)
//            .user(USER_1)
//            .certificates(List.of(CERTIFICATE_1))
//            .cost(CERTIFICATE_1.getPrice())
//            .datePurchase(LocalDateTime.now())
//            .build();
//
//
//    private static final List<User> LIST = List.of(USER_1, USER_2);
//
//    @BeforeEach
//    void setUp() {
//        userService = new UserServiceImpl(mockRepository, mockCreateUserModelMapper,
//                mockModelReadMapper, mockCertificateService, mockCreateOrderMapper,
//                mockCreateCertificateMapper, mockOnlyCertificateReadMapper,
//                mockOrderRepository, mockOnlyCertificateMapper,languageMassage);
//    }
//
//    @AfterEach
//    void tearDown() {
//        userService = null;
//    }
//
//    @Test
//    void getAllEntity() {
//        int limit = 1;
//        int offset = 2;
//        Mockito.when(mockRepository.getAllEntity(limit, offset)).thenReturn(LIST);
//        mockRepository.getAllEntity(limit, offset);
//        Assertions.assertNotNull(LIST);
//        Mockito.verify(mockRepository).getAllEntity(limit, offset);
//        Assertions.assertEquals(LIST.size(), 2);
//    }
//
//    @Test
//    void saveEntity() {
//        User user = User.builder()
//                .id(5L)
//                .nickName("Ben13")
//                .email("ben13@gmail.com")
//                .build();
//        Mockito.doNothing().when(mockRepository).addEntity(user);
//        mockRepository.addEntity(user);
//        Assertions.assertEquals(user.getNickName(), "Ben13");
//        Assertions.assertEquals(user.getEmail(), "ben13@gmail.com");
//    }
//
//    @Test
//    void updateEntity() {
//        User user = USER_2;
//        Assertions.assertEquals(user.getNickName(), "Svetlana");
//        String name = "Svetick";
//        user.setNickName(name);
//        Mockito.doNothing().when(mockRepository).updateEntity(user);
//        mockRepository.updateEntity(user);
//        Assertions.assertEquals(user.getNickName(), name);
//    }
//
//    @Test
//    void getEntity() {
//        Mockito.when(mockRepository.getEntityById(3L)).thenReturn(Optional.of(USER_1));
//        Optional<ReadUser> user = userService.findById(3);
//        Assertions.assertNotNull(user);
//        Mockito.verify(mockRepository).getEntityById(Mockito.anyLong());
//    }
//
//    @Test
//    void deleteEntity() {
//        Mockito.when(mockRepository.getEntityById(3L)).thenReturn(Optional.of(USER_1));
//        mockRepository.deleteEntity(3);
//        Mockito.when(mockRepository.getEntityById(3)).thenReturn(null);
//    }
//
//    @Test
//    void countAllUsers() {
//        Mockito.when(mockRepository.getAllEntity(1, 2)).thenReturn(LIST);
//        List<User> list = mockRepository.getAllEntity(1, 2);
//        Assertions.assertEquals(list.size(), 2);
//        Mockito.when(mockRepository.countAllUsers()).thenReturn(2);
//        int count = mockRepository.countAllUsers();
//        Assertions.assertEquals(count, 2);
//    }
//
//    @Test
//    void getUserByName() {
//        String name = "Andrei";
//        Mockito.when(mockRepository.getUserByName(name)).thenReturn(Optional.ofNullable(USER_1));
//        Optional<User> user = mockRepository.getUserByName(name);
//        Assertions.assertNotNull(user);
//        Assertions.assertEquals(user.get().getId(), 3L);
//
//    }
//
//    @Test
//    void purchaseCertificate() {
//
//        Mockito.when(mockOrderRepository.saveOrder(ORDER)).thenReturn(ORDER);
//        Order order = mockOrderRepository.saveOrder(ORDER);
//        Assertions.assertEquals(order.getCost(), CERTIFICATE_1.getPrice());
//    }
//}