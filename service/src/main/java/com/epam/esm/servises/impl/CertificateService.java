package com.epam.esm.servises.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.repositorys.impl.CertificateDao;
import com.epam.esm.servises.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CertificateService implements EntityService<Certificate> {

    @Autowired
    private CertificateDao certificateDao;

    @Override
    @Transactional
    public List<Certificate> getAllEntity() {
        return certificateDao.getAll();
    }

    @Override
    @Transactional
    public void saveEntity(Certificate certificate) {
        certificateDao.save(certificate);
    }

    @Override
    @Transactional
    public Certificate getEntity(long id) {
        return certificateDao.get(id);
    }

    @Override
    @Transactional
    public void deleteEntity(long id) {
        certificateDao.delete(id);
    }
}
