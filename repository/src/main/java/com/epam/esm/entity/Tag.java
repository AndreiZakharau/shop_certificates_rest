package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "tagName")
@ToString(exclude = "certificate")
@Builder
@Entity
@Table(name = "tag", schema = "buy_certificate_rest_dev")
public class Tag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tag_name")
    private String tagName;

    @Builder.Default
    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    private List<Certificate> certificates = new ArrayList<>();

}