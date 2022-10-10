package com.epam.esm.mapper.impl.certificateMapper;

import com.epam.esm.mapper.Mapper;
import com.epam.esm.model.certificate.ModelCertificate;
import com.epam.esm.model.certificate.OnlyCertificate;
import org.springframework.stereotype.Service;

@Service
public class ModelCertificateInOnlyCertificateMapper implements Mapper<ModelCertificate, OnlyCertificate> {
    @Override
    public OnlyCertificate mapFrom(ModelCertificate object) {
        return new OnlyCertificate(
                object.getId(),
                object.getCertificateName(),
                object.getDescription(),
                object.getPrice(),
                object.getDuration(),
                object.getCreateDate(),
                object.getLastUpdateDate()
        );
    }
}
