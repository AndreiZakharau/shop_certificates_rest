package com.epam.esm.models.tags;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlyTag {
    private long id;
    private String tagName;
}
//public record OnlyTag(long id, String tagName) {
//
//}
