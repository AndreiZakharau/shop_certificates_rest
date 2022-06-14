package com.epam.esm.repositorys;

import com.epam.esm.entity.Certificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CertificateRepository extends EntityRepository<Certificate> {
    @Override
    Page<Certificate> getAllEntity(Pageable pageable);

    @Override
    Optional<Certificate> getEntity(long id);

    @Override
    void addEntity(Certificate certificate);

    @Override
    void deleteEntity(long id);
}
