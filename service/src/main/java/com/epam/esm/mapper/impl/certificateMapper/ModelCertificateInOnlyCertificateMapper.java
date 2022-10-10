package com.epam.esm.mapper.impl.certificateMapper;

import com.epam.esm.mapper.Mapper;
import com.epam.esm.Dto.certificateDto.ReadCertificate;
import com.epam.esm.Dto.certificateDto.CreateCertificate;
import org.springframework.stereotype.Service;

@Service
public class ModelCertificateInOnlyCertificateMapper implements Mapper<ReadCertificate, CreateCertificate> {
    @Override
    public CreateCertificate mapFrom(ReadCertificate object) {
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
}
