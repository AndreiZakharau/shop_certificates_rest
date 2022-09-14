package com.epam.esm.models.orders;

import com.epam.esm.models.certificates.ModelCertificate;
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
    List<ModelCertificate> certificates;
    private double cost;
    private LocalDateTime datePurchase;
}
