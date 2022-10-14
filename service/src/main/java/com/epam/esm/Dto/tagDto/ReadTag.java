package com.epam.esm.Dto.tagDto;

import com.epam.esm.Dto.certificateDto.CreateCertificate;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadTag extends RepresentationModel<ReadTag> {

    private long id;
    private String tagName;
    List<CreateCertificate> certificate;
}





