package com.epam.esm.mapper.impl.tagMapper;

import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCreateCertificateFromCertificate;
import com.epam.esm.Dto.tagDto.ReadTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TransitionReadTagFromTag implements Mapper<Tag, ReadTag> {

    private final TransitionCreateCertificateFromCertificate certificateReadMapper;

    @Override
    public ReadTag mapFrom(Tag object) {
        return new ReadTag(
                object.getId(),
                object.getTagName(),
                certificateReadMapper.buildListCertificates(object.getCertificates())
        );
    }

    public List<ReadTag> buildListTag(List<Tag> tags) {
        List<ReadTag> readTags = new ArrayList<>();
        for (Tag tag : tags) {
            ReadTag model = ReadTag.builder()
                    .id(tag.getId())
                    .tagName(tag.getTagName())
                    .certificate(certificateReadMapper.buildListCertificates(tag.getCertificates()))
                    .build();
            readTags.add(model);
        }
        return readTags;
    }
}
