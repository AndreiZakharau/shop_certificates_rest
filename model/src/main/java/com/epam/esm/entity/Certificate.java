package com.epam.esm.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(exclude = {"tags"})
@Builder
@Entity
@Table(name = "gift_certificate")
public class Certificate extends BaseEntity {

    private String certificateName;
    private String description;
    private double price;
    private int duration;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime create_date;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime last_update_date;

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "certificates_tag",
            joinColumns = @JoinColumn(name = "certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags =  new ArrayList<>();

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getCertificates().add(this);
    }
}