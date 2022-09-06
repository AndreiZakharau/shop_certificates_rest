package com.epam.esm.mapper.impl.certificateMapper;

import com.epam.esm.entitys.Certificate;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.tagMapper.CreateTagFromOnlyTagMapper;
import com.epam.esm.models.certificates.ModelCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCertificateFromModelCertificateMapper implements Mapper<ModelCertificate, Certificate> {

    private final CreateTagFromOnlyTagMapper tagMapper;


    @Override
    public Certificate mapFrom(ModelCertificate object) {
        return Certificate.builder()
                .id(object.getId())
                .certificateName(object.getCertificateName())
                .description(object.getDescription())
                .duration(object.getDuration())
                .price(object.getPrice())
                .createDate(object.getCreateDate())
                .lastUpdateDate(object.getLastUpdateDate())
                .tags(tagMapper.buildListTags(object.getTags()))
                .build();
    }
}
