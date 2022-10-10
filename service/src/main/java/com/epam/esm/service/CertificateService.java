package com.epam.esm.service;

import com.epam.esm.entity.Certificate;
import com.epam.esm.Dto.certificateDto.CreateCertificate;

import java.util.List;
import java.util.Optional;

public interface CertificateService<T> {

    void saveEntity(Certificate certificate);

    void updateEntity(long id, CreateCertificate createCertificate);

    List<T> getAllEntity(int limit, int offset);

    Optional<T> getEntity(long id);

    void deleteEntity(long id);

    int countAllCertificates();

}
