package com.epam.esm.models.certificates;

import com.epam.esm.models.tags.OnlyTag;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelCertificate extends RepresentationModel<ModelCertificate> {

    private long id;
    private String certificateName;
    private String description;
    private double price;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private List<OnlyTag> tags;
}
