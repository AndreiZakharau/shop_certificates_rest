package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Entity
@Table
public class User extends BaseEntity <Long>{

    @Column(nullable = false, unique = true)
    private String nickName;
    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Order> orders;
}
