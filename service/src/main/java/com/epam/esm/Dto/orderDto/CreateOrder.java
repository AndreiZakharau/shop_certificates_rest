package com.epam.esm.Dto.orderDto;

import com.epam.esm.Dto.certificateDto.CreateCertificate;
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
public class CreateOrder extends RepresentationModel<CreateOrder> {

    CreateUser user;
    List<CreateCertificate> certificate;
    private double cost;
    public LocalDateTime datePurchase;
}
