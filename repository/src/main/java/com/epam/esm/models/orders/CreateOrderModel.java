package com.epam.esm.models.orders;

import com.epam.esm.models.certificates.OnlyCertificate;
import com.epam.esm.models.users.ReadUserModel;
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
