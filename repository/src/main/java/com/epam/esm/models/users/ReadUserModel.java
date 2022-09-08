package com.epam.esm.models.users;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadUserModel {

    private long id;
    private String nickName;
}
