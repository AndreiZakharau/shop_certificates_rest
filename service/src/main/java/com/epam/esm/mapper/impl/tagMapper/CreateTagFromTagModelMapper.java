package com.epam.esm.mapper.impl.tagMapper;

import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.certificateMapper.CreateCertificateFromOnlyCertificateMapper;
import com.epam.esm.Dto.tagDto.ReadTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateTagFromTagModelMapper implements Mapper<ReadTag, Tag> {

    private final CreateCertificateFromOnlyCertificateMapper mapper;

    @Override
    public Tag mapFrom(ReadTag object) {
        return Tag.builder()
                .id(object.getId())
                .tagName(object.getTagName())
                .certificates(mapper.buildListCertificates(object.getCertificate()))
                .build();
    }
}
