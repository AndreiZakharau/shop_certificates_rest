package com.epam.esm.servises.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repositorys.impl.CertificateRepositoryImpl;
import com.epam.esm.repositorys.impl.TagRepositoryImpl;
import com.epam.esm.servises.EntityService;
import com.epam.esm.util.impl.CertificateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("certificateService")
@RequiredArgsConstructor
public class CertificateServiceImpl implements EntityService<Certificate> {

    private final CertificateRepositoryImpl repository;
    private final CertificateValidator certificateValidator;
    private final TagRepositoryImpl tagRepositoryImpl;

    @Override
    @Transactional
    public Page<Certificate> getAllEntity(Pageable pageable) {
        return repository.getAllEntity(pageable);
    }

    @Override
    @Transactional
    public void saveEntity(Certificate certificate) {
        if (certificateValidator.isValid(certificate)){
            if(certificate.getCreateDate() == null){
                certificate.setCreateDate(LocalDateTime.now());
            }
            if(certificate.getLastUpdateDate() == null){
                certificate.setLastUpdateDate(certificate.getCreateDate().plusDays(certificate.getDuration()));
            }
            repository.addEntity(certificate);
        }else{
            //TODO бросаем исключение, что не валидно
        }
    }

    @Override
    public void updateEntity(long id, Certificate certificate) {
        if ((repository.getEntity(id)).isPresent()) {
            if (certificateValidator.isValid(certificate)) {
                repository.addEntity(certificate);
            }else{
                //TODO бросаем исключение, что не валидно
            }
        }else{
            //TODO (бросить исключение, что токого id нет)
        }
    }

    @Override
    @Transactional
    public Optional<Certificate> getEntity(long id) {
        if((repository.getEntity(id)).isEmpty()) {
            // TODO (бросить исключение, что токого id нет)
        }
            return repository.getEntity(id);
    }

    @Override
    @Transactional
    public void deleteEntity(long id) {
        if ((repository.getEntity(id)).isPresent()) {
            repository.deleteEntity(id);
        }else {
            //TODO (бросить исключение, что токого id нет)
        }
    }

    @Transactional
    public Page<Object[]> getAllCertificatesAndTags(Pageable pageable){
        return repository.getAllCertificatesAndTags(pageable);
    }

    @Transactional
    public void saveCertificatesTag(){

        Pageable pageable = null;
        Page<Tag> tagList = tagRepositoryImpl.getAllEntity(pageable); //Todo
        for (Tag t : tagList) {
            if (!repository.getCertificatesByTag(t).isEmpty()) {
                List<Certificate> certificateList = repository.getCertificatesByTag(t);
                for (Certificate c : certificateList) {
                    if(repository.getCertificateAndTag(c,t).isEmpty()) {
                        repository.saveCertificatesTag(c, t);
                    }
                }
            }
        }
    }

    public List<Certificate> getCertificatesByTag(Tag t){
        List<Certificate> list = repository.getCertificatesByTag(t);
        List<Certificate> result = null;
        if(list.isEmpty()){
            //TODO С таким тегом сертиыикаты не найдены
        }else
           result = repository.getCertificatesByTag(t);

        return result;
    }

    public List<Certificate> getCertificateByName(String name){
        List<Certificate> list = repository.getCertificatesByName(name);
        List<Certificate> result = null;
        if(list.isEmpty()){
            //TODO С таким именем сертиыикаты не найдены
        }else
            result = repository.getCertificatesByName(name);

        return result;
    }

}
