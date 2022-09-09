package com.epam.esm.mapper.impl.orderMapper;

import com.epam.esm.entitys.Order;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.certificateMapper.OnlyCertificateReadMapper;
import com.epam.esm.mapper.impl.userMapper.UserModelReadMapper;
import com.epam.esm.models.orders.OrderModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderReadMapper implements Mapper<Order, OrderModel> {

    private final UserModelReadMapper userModel;
    private final OnlyCertificateReadMapper certificate;

    @Override
    public OrderModel mapFrom(Order object) {
        return new OrderModel(
                object.getId(),
                userModel.mapFrom(object.getUser()),
                certificate.buildListCertificates(object.getCertificates()),
                object.getCost(),
                object.getDatePurchase()
        );
    }
}
