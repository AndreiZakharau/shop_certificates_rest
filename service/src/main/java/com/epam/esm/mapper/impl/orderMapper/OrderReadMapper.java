package com.epam.esm.mapper.impl.orderMapper;

import com.epam.esm.entity.Order;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.certificateMapper.ModelCertificateReadMapper;
import com.epam.esm.mapper.impl.userMapper.UserModelReadMapper;
import com.epam.esm.model.order.OrderModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderReadMapper implements Mapper<Order, OrderModel> {

    private final UserModelReadMapper userModel;
    private final ModelCertificateReadMapper certificate;

    @Override
    public OrderModel mapFrom(Order object) {
        return new OrderModel(
                object.getId(),
                userModel.mapFrom(object.getUser()),
                certificate.buildListModelCertificates(object.getCertificates()),
                object.getCost(),
                object.getDatePurchase()
        );
    }

    public List<OrderModel> buildOrderModel(List<Order> orders) {
        List<OrderModel> list = new ArrayList<>();
        for (Order order : orders) {
            OrderModel model = OrderModel.builder()
                    .id(order.getId())
                    .userModel(userModel.mapFrom(order.getUser()))
                    .certificates(certificate.buildListModelCertificates(order.getCertificates()))
                    .cost(order.getCost())
                    .datePurchase(order.getDatePurchase())
                    .build();
            list.add(model);
        }
        return list;
    }
}
