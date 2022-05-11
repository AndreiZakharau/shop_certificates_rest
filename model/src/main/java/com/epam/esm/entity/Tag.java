package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "tagName")
@ToString()
@Builder
@Entity
@Table
public class Tag extends BaseEntity <Long> {

    @Column(nullable = false, unique = true)
    private String tagName;

    @Builder.Default
    @ManyToMany(mappedBy = "tags")
    private List<Certificate> certificates = new ArrayList<>();
}
