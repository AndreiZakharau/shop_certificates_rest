package com.epam.esm.mapper.impl.orderMapper;

import com.epam.esm.entitys.Order;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.certificateMapper.ModelCertificateReadMapper;
import com.epam.esm.models.orders.ReadOrderModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateReadOrderModelMapper implements Mapper<Order, ReadOrderModel> {

    private final ModelCertificateReadMapper certificate;

    @Override
    public ReadOrderModel mapFrom(Order object) {
        return new ReadOrderModel(
                object.getId(),
                certificate.buildListModelCertificates(object.getCertificates()),
                object.getCost(),
                object.getDatePurchase()
        );
    }

    public List<ReadOrderModel> buildReadOrderModel(List<Order> orders) {
        List<ReadOrderModel> list = new ArrayList<>();
        for (Order order : orders) {
            ReadOrderModel readOrderModel = ReadOrderModel.builder()
                    .id(order.getId())
                    .certificates(certificate.buildListModelCertificates(order.getCertificates()))
                    .cost(order.getCost())
                    .datePurchase(order.getDatePurchase())
                    .build();
            list.add(readOrderModel);
        }
        return list;
    }
}
