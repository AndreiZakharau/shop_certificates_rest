package com.epam.esm.mapper.impl.orderMapper;

import com.epam.esm.entity.Order;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.certificateMapper.CreateCertificateFromOnlyCertificateMapper;
import com.epam.esm.mapper.impl.userMapper.CreateUserFromUserModelReadMapper;
import com.epam.esm.model.order.CreateOrderModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatOrderFromOrderModelMapper implements Mapper<CreateOrderModel, Order> {

    private final CreateUserFromUserModelReadMapper userMapper;
    private final CreateCertificateFromOnlyCertificateMapper certificateMapper;

    @Override
    public Order mapFrom(CreateOrderModel object) {
        return Order.builder()
                .user(userMapper.mapFrom(object.getUser()))
                .certificates(certificateMapper.buildListCertificates(object.getCertificate()))
                .cost(object.getCost())
                .datePurchase(object.getDatePurchase())
                .build();
    }
}
