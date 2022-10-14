package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends EntityRepository<Certificate> {
    @Override
    List<Certificate> getAllEntity(int limit, int offset);

    @Override
    Optional<Certificate> getEntityById(long id);

    @Override
    void addEntity(Certificate certificate);

    @Override
    void updateEntity(Certificate certificate);

//    @Override
    void deleteEntity(Certificate certificate);

    void saveCertificatesTag(long c, long t);

    List<Certificate> getCertificateAndTag(Certificate c, Tag t);

    List<Certificate> getCertificatesByName(String name);

    int countAllCertificates();

    List<Certificate> getCertificateByParameters(String name, List<String> tagNames, String description,
                                                 List<Double> prices, Integer page, Integer size);

    List<Certificate> getCertificatesByTags(List<String> tagNames);
}
