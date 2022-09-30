package com.epam.esm.models.orders;

import com.epam.esm.models.certificates.ModelCertificate;
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
public class OrderModel extends RepresentationModel<OrderModel> {

    private long id;
    ReadUserModel userModel;
    List<ModelCertificate> certificates;
    private double cost;
    private LocalDateTime datePurchase;
}
