package com.epam.esm.mapper.impl.certificateMapper;

import com.epam.esm.entitys.Certificate;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.models.certificates.OnlyCertificate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OnlyCertificateReadMapper implements Mapper<Certificate, OnlyCertificate> {
    @Override
    public OnlyCertificate mapFrom(Certificate object) {
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

    public List<OnlyCertificate> buildListCertificates(List<Certificate> list) {
        List<OnlyCertificate> certificates = new ArrayList<>();
        for (Certificate c : list) {
            OnlyCertificate certificate = new OnlyCertificate();
            certificate.setId(c.getId());
            certificate.setCertificateName(c.getCertificateName());
            certificate.setDescription(c.getDescription());
            certificate.setDuration(c.getDuration());
            certificate.setPrice(c.getPrice());
            certificate.setCreateDate(c.getCreateDate());
            certificate.setLastUpdateDate(c.getLastUpdateDate());
            certificates.add(certificate);
        }
        return certificates;
    }
}
