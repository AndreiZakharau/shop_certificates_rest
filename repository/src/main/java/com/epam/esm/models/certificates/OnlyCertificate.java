package com.epam.esm.models.certificates;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"certificateName","description"})
// extends RepresentationModel<OnlyCertificate> если понадобиться links
public class OnlyCertificate  {

    private long id;
    private String certificateName;
    private String description;
    private double price;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
}
