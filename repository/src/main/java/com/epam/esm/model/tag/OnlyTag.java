package com.epam.esm.model.tag;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlyTag extends RepresentationModel<OnlyTag> {
    private long id;
    private String tagName;
}
//public record OnlyTag(long id, String tagName) {
//
//}
