package com.epam.esm.models.certificates;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "tags")
@EqualsAndHashCode(of = {"certificateName","description"})
// extends RepresentationModel<ModelCertificateWithTags> если понадобится links
public class ModelCertificateWithTags  {

    private long id;
    private String certificateName;
    private String description;
    private double price;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
//    private List<TagModel> tags;
}
