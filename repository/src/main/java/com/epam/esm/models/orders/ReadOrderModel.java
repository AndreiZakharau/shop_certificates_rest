package com.epam.esm.models.orders;

import com.epam.esm.models.certificates.OnlyCertificate;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadOrderModel {

    private long id;
    List<OnlyCertificate> certificates;
    private LocalDateTime datePurchase;
}
