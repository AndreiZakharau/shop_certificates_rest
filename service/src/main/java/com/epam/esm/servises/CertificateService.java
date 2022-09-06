package com.epam.esm.servises;

import com.epam.esm.entitys.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateService <T>{

    void saveEntity(Certificate certificate);

    void updateEntity(long id, T t);

    List<T> getAllEntity(int limit, int offset);

    Optional<T> getEntity(long id);

    void deleteEntity(long id);

    int countAllCertificates();

}