package com.epam.esm.models.orders;

import com.epam.esm.models.certificates.OnlyCertificate;
import com.epam.esm.models.users.ReadUserModel;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderModel {
    ReadUserModel user;
    List<OnlyCertificate> certificate;
    private double cost;
    public LocalDateTime datePurchase;
}
