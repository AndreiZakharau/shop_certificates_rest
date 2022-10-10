package com.epam.esm.Dto.userDto;

import com.epam.esm.Dto.orderDto.ReadOrder;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadUser extends RepresentationModel<ReadUser> {

    private long id;
    private String nickName;
    List<ReadOrder> orders;
}
