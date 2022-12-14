package com.epam.esm.Dto.userDto;

import com.epam.esm.Dto.orderDto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private String email;
    List<OrderDto> orders;
}
