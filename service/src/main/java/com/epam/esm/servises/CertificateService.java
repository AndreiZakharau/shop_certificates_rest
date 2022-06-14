package com.epam.esm.servises;

import com.epam.esm.entity.Certificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CertificateService extends EntityService<Certificate> {
    @Override
    Page<Certificate> getAllEntity(Pageable pageable);

    @Override
    void saveEntity(Certificate certificate);

    @Override
    void updateEntity(long id, Certificate certificate);

    @Override
    Optional<Certificate> getEntity(long id);

    @Override
    void deleteEntity(long id);
}
