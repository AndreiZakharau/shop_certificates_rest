package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.util.List;

public interface CertificateRepository extends EntityRepository<Certificate> {

    void deleteEntity(Certificate certificate);

    void saveCertificatesTag(long c, long t);

    List<Certificate> getCertificateAndTag(Certificate c, Tag t);

    List<Certificate> getCertificatesByName(String name);

    int countAllCertificates();

    List<Certificate> getCertificateByParameters(String name, List<String> tagNames, String description,
                                                 List<Double> prices,List<String>sortColumns, List<String>orderTypes, int page, int size);

    List<Certificate> getCertificatesByTags(List<String> tagNames);
}
