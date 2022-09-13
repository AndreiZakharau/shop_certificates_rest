package com.epam.esm.mapper.impl.orderMapper;

import com.epam.esm.entitys.Order;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.certificateMapper.CreateCertificateFromOnlyCertificateMapper;
import com.epam.esm.models.orders.ReadOrderModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrderFromReadOrderModelMapper implements Mapper<ReadOrderModel, Order> {

    private final CreateCertificateFromOnlyCertificateMapper certificateMapper;
    @Override
    public Order mapFrom(ReadOrderModel object) {
        return Order.builder()
                .certificates(certificateMapper.buildListCertificates(object.getCertificates()))
                .cost(object.getCost())
                .datePurchase(object.getDatePurchase())
                .build();
    }
}
