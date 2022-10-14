package com.epam.esm.mapper.impl.orderMapper;

import com.epam.esm.Dto.orderDto.CreateOrder;
import com.epam.esm.entity.Order;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateFromCreateCertificate;
import com.epam.esm.mapper.impl.userMapper.TransitionUserFromCreateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransitionOrderFromCreateOrder implements Mapper<CreateOrder, Order> {

    private final TransitionUserFromCreateUser userMapper;
    private final TransitionCertificateFromCreateCertificate certificateMapper;

    @Override
    public Order mapFrom(CreateOrder object) {
        return Order.builder()
                .user(userMapper.mapFrom(object.getUser()))
                .certificates(certificateMapper.buildListCertificates(object.getCertificate()))
                .cost(object.getCost())
                .datePurchase(object.getDatePurchase())
                .build();
    }
}
