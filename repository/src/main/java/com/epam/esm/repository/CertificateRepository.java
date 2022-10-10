package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;

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
    default void updateEntity(Certificate certificate) {

    }
}
