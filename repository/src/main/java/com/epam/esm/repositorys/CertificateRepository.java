package com.epam.esm.repositorys;

import com.epam.esm.entity.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends EntityRepository<Certificate> {
    @Override
    List<Certificate> getAllEntity();

    @Override
    Optional<Certificate> getEntity(long id);

    @Override
    void addEntity(Certificate certificate);

    @Override
    void deleteEntity(long id);
}
