package com.epam.esm.mapper.impl.certificateMapper;

import com.epam.esm.entitys.Certificate;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.mapper.impl.tagMapper.OnlyTagReadMapper;
import com.epam.esm.models.certificates.ModelCertificate;
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
            ModelCertificate model = new ModelCertificate();
            model.setId(certificate.getId());
            model.setCertificateName(certificate.getCertificateName());
            model.setDescription(certificate.getDescription());
            model.setDuration(certificate.getDuration());
            model.setPrice(certificate.getPrice());
            model.setCreateDate(certificate.getCreateDate());
            model.setLastUpdateDate(certificate.getLastUpdateDate());
            model.setTags(tagReadMapper.buildListOnlyTag(certificate.getTags()));
            list.add(model);
        }
        return list;
    }
}
