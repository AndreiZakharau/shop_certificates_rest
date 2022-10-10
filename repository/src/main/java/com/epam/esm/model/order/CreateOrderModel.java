package com.epam.esm.model.order;

import com.epam.esm.model.certificate.OnlyCertificate;
import com.epam.esm.model.user.ReadUserModel;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderModel extends RepresentationModel<CreateOrderModel> {

    ReadUserModel user;
    List<OnlyCertificate> certificate;
    private double cost;
    public LocalDateTime datePurchase;
}
