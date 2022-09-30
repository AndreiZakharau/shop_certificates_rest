package com.epam.esm.models.users;

import com.epam.esm.models.orders.ReadOrderModel;
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
