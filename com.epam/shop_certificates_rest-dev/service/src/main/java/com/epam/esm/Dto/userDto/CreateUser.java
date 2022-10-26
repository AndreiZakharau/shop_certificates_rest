package com.epam.esm.Dto.userDto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUser extends RepresentationModel<CreateUser> {

    private String nickName;
    private String email;
}
