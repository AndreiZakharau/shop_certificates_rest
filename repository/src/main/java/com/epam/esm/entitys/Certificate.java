package com.epam.esm.entitys;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"certificateName","description"})
@ToString(exclude = {"tags", "orders"})
@Builder
@Entity
@Table(name = "gift_certificate")
public class Certificate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "certificate_name")
    private String certificateName;
    private String description;
    private double price;
    private int duration;
    @Column(name = "create_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;
    @Column(name = "last_update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "certificates_tag",
            joinColumns = @JoinColumn(name = "certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags =  new HashSet<>();

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getCertificates().add(this);
    }

    @ManyToMany(mappedBy = "certificates",fetch = FetchType.EAGER)
    private List<Order> orders;

}