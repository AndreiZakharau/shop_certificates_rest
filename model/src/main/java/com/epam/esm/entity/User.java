package com.epam.esm.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Entity
@Table
public class User extends BaseEntity{

    private String firstname;
    private String lastname;
}
