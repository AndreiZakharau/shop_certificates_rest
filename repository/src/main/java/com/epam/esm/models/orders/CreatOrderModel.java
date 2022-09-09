package com.epam.esm.models.orders;

import com.epam.esm.models.certificates.OnlyCertificate;
import com.epam.esm.models.users.ReadUserModel;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatOrderModel {
    ReadUserModel user;
    List<OnlyCertificate> certificate;
    private BigDecimal cost;
    public LocalDateTime datePurchase;
}
