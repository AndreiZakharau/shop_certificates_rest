package com.epam.esm.Dto.certificateDto;

import com.epam.esm.Dto.tagDto.TagDto;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadCertificate extends RepresentationModel<ReadCertificate> {

    private long id;
    private String certificateName;
    private String description;
    private double price;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private List<TagDto> tags;
}
