package com.epam.esm.model.user;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadUserModel extends RepresentationModel<ReadUserModel> {

    private long id;
    private String nickName;
}
