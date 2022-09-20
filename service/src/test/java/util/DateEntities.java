package util;

import com.epam.esm.entitys.User;
import lombok.experimental.UtilityClass;

import java.util.List;
@UtilityClass
public class DateEntities {

    User user1 = User.builder()
            .id(3L)
            .nickName("Andrei")
            .email("andrei666@gmail.com")
            .build();

    User user2 = User.builder()
            .id(4L)
            .nickName("Svetlana")
            .email("svetic_7@gmail.com")
            .build();

    List<User> listUsers = List.of(user1, user2);
}
