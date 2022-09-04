package com.epam.esm.servises;

import com.epam.esm.entitys.Certificate;
import com.epam.esm.models.certificates.OnlyCertificate;

import java.util.List;
import java.util.Optional;

public interface CertificateService {

    void saveEntity(Certificate certificate);

    void updateEntity(long id, OnlyCertificate certificateModel);

    List<OnlyCertificate> getAllEntity(int limit, int offset);

    Optional<Certificate> getEntity(long id);

    void deleteEntity(long id);

    int countAllCertificates();

}
