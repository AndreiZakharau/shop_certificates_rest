package com.epam.esm.mapper.impl.certificateMapper;

import com.epam.esm.entity.Certificate;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.Dto.certificateDto.CreateCertificate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateCertificateFromOnlyCertificateMapper implements Mapper<CreateCertificate, Certificate> {

    @Override
    public Certificate mapFrom(CreateCertificate object) {
        return Certificate.builder()
                .id(object.getId())
                .certificateName(object.getCertificateName())
                .description(object.getDescription())
                .duration(object.getDuration())
                .price(object.getPrice())
                .createDate(object.getCreateDate())
                .lastUpdateDate(object.getLastUpdateDate())
                .build();
    }

    public List<Certificate> buildListCertificates(List<CreateCertificate> list) {
        List<Certificate> certificates = new ArrayList<>();
        for (CreateCertificate c : list) {
            Certificate cm = Certificate.builder()
                    .id(c.getId())
                    .certificateName(c.getCertificateName())
                    .description(c.getDescription())
                    .duration(c.getDuration())
                    .price(c.getPrice())
                    .createDate(c.getCreateDate())
                    .lastUpdateDate(c.getLastUpdateDate())
                    .build();
            certificates.add(cm);
        }
        return certificates;
    }
}
