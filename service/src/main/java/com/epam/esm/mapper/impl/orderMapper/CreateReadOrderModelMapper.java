package com.epam.esm.mapper.impl.orderMapper;

import com.epam.esm.entitys.Order;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.certificateMapper.OnlyCertificateReadMapper;
import com.epam.esm.models.orders.ReadOrderModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateReadOrderModelMapper implements Mapper<Order, ReadOrderModel> {

    private final OnlyCertificateReadMapper certificate;

    @Override
    public ReadOrderModel mapFrom(Order object) {
        return new ReadOrderModel(
                object.getId(),
                certificate.buildListCertificates(object.getCertificates()),
                object.datePurchase
        );
    }

    public List<ReadOrderModel> buildReadOrderModel(List<Order> orders){
        List<ReadOrderModel> list = new ArrayList<>();
        for (Order order : orders){
            ReadOrderModel readOrderModel = new ReadOrderModel();
            readOrderModel.setId(order.getId());
            certificate.buildListCertificates(order.getCertificates());
            readOrderModel.setDatePurchase(order.datePurchase);
            list.add(readOrderModel);
        }
        return list;
    }
}
