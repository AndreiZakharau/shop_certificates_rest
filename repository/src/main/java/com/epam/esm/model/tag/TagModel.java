package com.epam.esm.model.tag;

import com.epam.esm.model.certificate.OnlyCertificate;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagModel extends RepresentationModel<TagModel> {

    private long id;
    private String tagName;
    List<OnlyCertificate> certificate;
}
//public record TagModel (long id,
//        String tagName,
//        OnlyCertificate certificate) {

//}



