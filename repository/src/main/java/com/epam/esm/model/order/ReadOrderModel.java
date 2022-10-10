package com.epam.esm.model.order;

import com.epam.esm.model.certificate.ModelCertificate;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadOrderModel extends RepresentationModel<ReadOrderModel> {

    private long id;
    List<ModelCertificate> certificates;
    private double cost;
    private LocalDateTime datePurchase;
}
