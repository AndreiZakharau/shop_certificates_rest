package com.epam.esm.mapper.impl.certificateMapper;

import com.epam.esm.entitys.Certificate;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.models.certificates.OnlyCertificate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateCertificateMapper implements Mapper<OnlyCertificate, Certificate> {
    @Override
    public Certificate mapFrom(OnlyCertificate object) {
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

        public List<Certificate> buildListCertificates(List<OnlyCertificate> list) {
        List<Certificate> certificates = new ArrayList<>();
        for (OnlyCertificate c : list) {
            Certificate cm = new Certificate();
            cm.setId(c.getId());
            cm.setCertificateName(c.getCertificateName());
            cm.setDescription(c.getDescription());
            cm.setDuration(c.getDuration());
            cm.setPrice(c.getPrice());
            cm.setCreateDate(c.getCreateDate());
            cm.setLastUpdateDate(c.getLastUpdateDate());
            certificates.add(cm);
        }
        return certificates;
    }
}
