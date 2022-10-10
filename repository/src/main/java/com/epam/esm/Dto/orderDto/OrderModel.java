package com.epam.esm.Dto.orderDto;

import com.epam.esm.Dto.certificateDto.ReadCertificate;
import com.epam.esm.Dto.userDto.CreateUser;
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
    CreateUser userModel;
    List<ReadCertificate> certificates;
    private double cost;
    private LocalDateTime datePurchase;
}
