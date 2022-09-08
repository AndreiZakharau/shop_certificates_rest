package com.epam.esm.models.users;

import com.epam.esm.models.orders.ReadOrderModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    private long id;
    private String nickName;
    List<ReadOrderModel> orders;
}
