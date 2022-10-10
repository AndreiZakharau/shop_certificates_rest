package com.epam.esm.mapper.impl.certificateMapper;

import com.epam.esm.entity.Certificate;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.tagMapper.OnlyTagReadMapper;
import com.epam.esm.model.certificate.ModelCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelCertificateReadMapper implements Mapper<Certificate, ModelCertificate> {

    private final OnlyTagReadMapper tagReadMapper;

    @Override
    public ModelCertificate mapFrom(Certificate object) {
        return new ModelCertificate(
                object.getId(),
                object.getCertificateName(),
                object.getDescription(),
                object.getPrice(),
                object.getDuration(),
                object.getCreateDate(),
                object.getLastUpdateDate(),
                tagReadMapper.buildListOnlyTag(object.getTags())
        );
    }

    public List<ModelCertificate> buildListModelCertificates(List<Certificate> certificates) {
        List<ModelCertificate> list = new ArrayList<>();
        for (Certificate certificate : certificates) {
            ModelCertificate model = ModelCertificate.builder()
                    .id(certificate.getId())
                    .certificateName(certificate.getCertificateName())
                    .description(certificate.getDescription())
                    .price(certificate.getPrice())
                    .duration(certificate.getDuration())
                    .createDate(certificate.getCreateDate())
                    .lastUpdateDate(certificate.getLastUpdateDate())
                    .tags(tagReadMapper.buildListOnlyTag(certificate.getTags()))
                    .build();
            list.add(model);
        }
        return list;
    }
}
