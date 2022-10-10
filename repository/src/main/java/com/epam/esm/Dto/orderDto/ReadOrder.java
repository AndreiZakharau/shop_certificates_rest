package com.epam.esm.Dto.orderDto;

import com.epam.esm.Dto.certificateDto.ReadCertificate;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadOrder extends RepresentationModel<ReadOrder> {

    private long id;
    List<ReadCertificate> certificates;
    private double cost;
    private LocalDateTime datePurchase;
}
