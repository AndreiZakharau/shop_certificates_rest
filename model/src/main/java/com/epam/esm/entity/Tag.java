package com.epam.esm.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(exclude = "gift_certificate")
@Builder
@Entity
@Table
public class Tag extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String tagName;

    @Builder.Default
    @ManyToMany(mappedBy = "tags")
    private List<Certificate> certificates = new ArrayList<>();
}
