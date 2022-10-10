package com.epam.esm.mapper.impl.certificateMapper;

import com.epam.esm.entity.Certificate;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.Dto.certificateDto.CreateCertificate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OnlyCertificateReadMapper implements Mapper<Certificate, CreateCertificate> {

    @Override
    public CreateCertificate mapFrom(Certificate object) {
        return new CreateCertificate(
                object.getId(),
                object.getCertificateName(),
                object.getDescription(),
                object.getPrice(),
                object.getDuration(),
                object.getCreateDate(),
                object.getLastUpdateDate()
        );
    }

    public List<CreateCertificate> buildListCertificates(List<Certificate> list) {
        List<CreateCertificate> certificates = new ArrayList<>();
        for (Certificate c : list) {
            CreateCertificate certificate = CreateCertificate.builder()
                    .id(c.getId())
                    .certificateName(c.getCertificateName())
                    .description(c.getDescription())
                    .duration(c.getDuration())
                    .price(c.getPrice())
                    .createDate(c.getCreateDate())
                    .lastUpdateDate(c.getLastUpdateDate())
                    .build();
            certificates.add(certificate);
        }
        return certificates;
    }
}
