package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode(of = "tagName")
@ToString(exclude = "certificates")
@Builder
@Entity
@Table
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tag_name")
    private String tagName;

    @Builder.Default
    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    private List<Certificate> certificates = new ArrayList<>();


    public Tag() {
    }

    public Tag(Long id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }

    public Tag(Long id, String tagName, List<Certificate> certificates) {
        this.id = id;
        this.tagName = tagName;
        this.certificates = certificates;
    }
}
