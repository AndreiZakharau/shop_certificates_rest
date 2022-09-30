package com.epam.esm.mapper.impl.tagMapper;

import com.epam.esm.entitys.Tag;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.certificateMapper.OnlyCertificateReadMapper;
import com.epam.esm.models.tags.TagModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TagModelReadMapper implements Mapper<Tag, TagModel> {

    private final OnlyCertificateReadMapper certificateReadMapper;

    @Override
    public TagModel mapFrom(Tag object) {
        return new TagModel(
                object.getId(),
                object.getTagName(),
                certificateReadMapper.buildListCertificates(object.getCertificates())
        );
    }

    public List<TagModel> buildListTag(List<Tag> tags) {
        List<TagModel> tagModels = new ArrayList<>();
        for (Tag tag : tags) {
            TagModel model = TagModel.builder()
                    .id(tag.getId())
                    .tagName(tag.getTagName())
                    .certificate(certificateReadMapper.buildListCertificates(tag.getCertificates()))
                    .build();
            tagModels.add(model);
        }
        return tagModels;
    }
}
