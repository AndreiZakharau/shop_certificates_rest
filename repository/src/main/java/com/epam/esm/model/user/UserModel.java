package com.epam.esm.model.user;

import com.epam.esm.model.order.ReadOrderModel;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel extends RepresentationModel<UserModel> {

    private long id;
    private String nickName;
    List<ReadOrderModel> orders;
}
