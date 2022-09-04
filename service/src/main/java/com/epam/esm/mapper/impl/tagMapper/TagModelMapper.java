package com.epam.esm.mapper.impl.tagMapper;

import com.epam.esm.entitys.Tag;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.certificateMapper.CreateCertificateMapper;
import com.epam.esm.models.tags.TagModel;
import com.epam.esm.repositorys.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TagModelMapper implements Mapper<TagModel, Tag> {

    private final CreateCertificateMapper mapper;
    private final CertificateRepository certificateRepository;
    @Override
    public Tag mapFrom(TagModel object) {
        return Tag.builder()
                .id(object.getId())
                .tagName(object.getTagName())
                .certificates(mapper.buildListCertificates(object.getCertificate()))
                .build();
    }

        public List<Tag> buildListTag(List<TagModel> models) {
        List<Tag> tags = new ArrayList<>();
        for (TagModel model : models) {
            Tag tag = new Tag();
            tag.setId(model.getId());
            tag.setTagName(model.getTagName());
            tag.getCertificates();
        }
        return tags;
    }

}
