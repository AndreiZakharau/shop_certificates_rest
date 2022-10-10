package com.epam.esm.mapper.impl.orderMapper;

import com.epam.esm.entity.Order;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.certificateMapper.CreateCertificateFromModelCertificateMapper;
import com.epam.esm.Dto.orderDto.ReadOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrderFromReadOrderModelMapper implements Mapper<ReadOrder, Order> {

    private final CreateCertificateFromModelCertificateMapper certificateMapper;

    @Override
    public Order mapFrom(ReadOrder object) {
        return Order.builder()
                .certificates(certificateMapper.buildListCertificateFromModelCertificate(object.getCertificates()))
                .cost(object.getCost())
                .datePurchase(object.getDatePurchase())
                .build();
    }
}
