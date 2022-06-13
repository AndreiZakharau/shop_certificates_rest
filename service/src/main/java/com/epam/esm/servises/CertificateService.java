package com.epam.esm.servises;

import com.epam.esm.entity.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateService extends EntityService<Certificate> {
    @Override
    List<Certificate> getAllEntity();

    @Override
    void saveEntity(Certificate certificate);

    @Override
    void updateEntity(long id, Certificate certificate);

    @Override
    Optional<Certificate> getEntity(long id);

    @Override
    void deleteEntity(long id);
}
