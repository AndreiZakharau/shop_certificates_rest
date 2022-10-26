package com.epam.esm.service;

import com.epam.esm.Dto.certificateDto.CertificateDto;
import com.epam.esm.Dto.certificateDto.CreateCertificate;
import com.epam.esm.Dto.certificateDto.ReadCertificate;

import java.util.List;

public interface CertificateService extends EntityService<ReadCertificate, CreateCertificate, CertificateDto> {

    @Override
    void saveEntity(CreateCertificate createCertificate);

    @Override
    void updateEntity(long id, CertificateDto certificateDto);

    @Override
    List<ReadCertificate> getAllEntity(int limit, int offset);

    @Override
    ReadCertificate findById(long id);

    @Override
    void deleteEntity(long id);

    @Override
    Long countAll();

    List<ReadCertificate> getCertificatesByTags(List<String> tagNames);

    List<ReadCertificate> getCertificateByParameters(
            String name, List<String> tagNames, String description, List<Double> price,
            List<String> sortColumns, List<String> orderTypes, int offset, int size);
}
