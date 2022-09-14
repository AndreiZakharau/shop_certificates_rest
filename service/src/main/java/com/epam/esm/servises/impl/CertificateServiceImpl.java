package com.epam.esm.servises.impl;

import com.epam.esm.entitys.Certificate;
import com.epam.esm.entitys.Tag;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.mapper.impl.certificateMapper.CreateCertificateFromOnlyCertificateMapper;
import com.epam.esm.mapper.impl.certificateMapper.ModelCertificateReadMapper;
import com.epam.esm.models.certificates.ModelCertificate;
import com.epam.esm.models.certificates.OnlyCertificate;
import com.epam.esm.repositorys.impl.CertificateRepositoryImpl;
import com.epam.esm.repositorys.impl.TagRepositoryImpl;
import com.epam.esm.servises.CertificateService;
import com.epam.esm.util.impl.CertificateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("certificateService")
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService<ModelCertificate> {

    private final CertificateRepositoryImpl repository;
    private final CertificateValidator certificateValidator;
    private final TagRepositoryImpl tagRepository;
    private final ModelCertificateReadMapper readMapper;
    private final CreateCertificateFromOnlyCertificateMapper onlyCertificateMapper;

    @Override
    @Transactional
    public List<ModelCertificate> getAllEntity(int limit, int offset) {
        List<Certificate> list = repository.getAllEntity(limit, offset);
        return readMapper.buildListModelCertificates(list);

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
            saveCertificatesTag();
        }else{
            System.out.println("NOT VALID!!!");//TODO бросаем исключение, что не валидно
        }
    }
    // TODO ?????
    @Override
    @Transactional
    public void updateEntity(long id, OnlyCertificate model) {
        Optional <Certificate> c = repository.getEntityById(id);
        if (c.isPresent()) {
            model.setId(id);
            if(model.getCertificateName() == null)
                model.setCertificateName(c.get().getCertificateName());
            if(model.getDescription()== null)
                model.setDescription(c.get().getDescription());
            if(model.getPrice() <= 0)
                model.setPrice(c.get().getPrice());
            if(model.getDuration()<=0) {
                model.setDuration(c.get().getDuration());
                model.setLastUpdateDate(LocalDateTime.now().plusDays(c.get().getDuration()));
            }else{
                model.setLastUpdateDate(LocalDateTime.now().plusDays(model.getDuration()));
            }
            if(model.getCreateDate()==null)
                model.setCreateDate(c.get().getCreateDate());
            if(model.getLastUpdateDate()==null)
                model.setLastUpdateDate(c.get().getLastUpdateDate());
            Certificate certificate = onlyCertificateMapper.mapFrom(model);

            if (certificateValidator.isValid(certificate)) {
                repository.updateEntity(certificate);
            }else{
                //TODO бросаем исключение, что не валидно
            }
        }else{
           // TODO (бросить исключение, что токого id нет)
        }
    }

    @Override
    @Transactional
    public Optional<ModelCertificate> getEntity(long id) {
        Optional<Certificate> c = repository.getEntityById(id);
        if(c.isEmpty()) {
            // TODO (бросить исключение, что токого id нет)
        }
            return c.map(readMapper::mapFrom);
    }

    @Override
    @Transactional
    public void deleteEntity(long id) {
        Optional<Certificate> c = repository.getEntityById(id);
        if (c.isPresent()) {
            repository.deleteEntity(c.get());
        }else {
            //TODO (бросить исключение, что токого id нет)
        }
    }

    @Override
    @Transactional
    public int countAllCertificates() {
        return repository.countAllCertificates();
    }

//    @Transactional
//    public List<Certificate> getAllCertificates(int limit, int offset){
//        return repository.getAllCertificates(limit,offset);
//    }

    @Transactional
    public void saveCertificatesTag(){

        List<Tag> tagList = tagRepository.getOnlyTags();
        for (Tag t : tagList) {
            List<Certificate> certificateList = repository.getCertificatesByName(t.getTagName());
            if (!certificateList.isEmpty()) {
                for (Certificate c : certificateList) {
                    if(repository.getCertificateAndTag(c,t).isEmpty()) {
                        repository.saveCertificatesTag(c.getId(), t.getId());
                    }
                }
            }
        }
    }

    @Transactional
    public List<ModelCertificate> getCertificatesByTag(String tagName){
        List<Certificate> list ;
        Optional<Tag> tag = tagRepository.getTagByName(tagName);
        if(tag.isEmpty()){
            throw new NoSuchEntityException("This tag is empty!!!");
        }else {
            list = repository.getCertificatesByName(tagName); //TODO переделать оьвет
        }
        return readMapper.buildListModelCertificates(list);
    }

    @Transactional
    public List<ModelCertificate> getCertificateByName(String name) {
        List<Certificate> list = repository.getCertificatesByName(name);
        if (list.isEmpty()) {
            //TODO С таким именем сертиыикаты не найдены
        }
        return readMapper.buildListModelCertificates(list);
    }

    //TODO
    @Transactional
    public List<ModelCertificate> getSortedCertificates(int limit, int offset,String desc) {
        List<Certificate> list = new ArrayList<>();
        if (!desc.equals("desc"))
            list = repository.getAllEntity(limit, offset);
        else
            list = repository.getSortedCertificates(limit, offset);
        return readMapper.buildListModelCertificates(list);
    }
}
