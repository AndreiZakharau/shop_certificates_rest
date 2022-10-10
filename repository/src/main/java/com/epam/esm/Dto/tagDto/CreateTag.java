package com.epam.esm.Dto.tagDto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTag extends RepresentationModel<CreateTag> {

    private long id;
    private String tagName;
}
//public record OnlyTag(long id, String tagName) {
//
//}
