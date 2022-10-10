package com.epam.esm.servise.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectDataException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.mapper.impl.certificateMapper.CreateCertificateFromOnlyCertificateMapper;
import com.epam.esm.mapper.impl.certificateMapper.ModelCertificateReadMapper;
import com.epam.esm.model.certificate.ModelCertificate;
import com.epam.esm.model.certificate.OnlyCertificate;
import com.epam.esm.repository.impl.CertificateRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.servise.CertificateService;
import com.epam.esm.util.impl.CertificateValidator;
import com.epam.esm.util.messange.LanguageMassage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("certificateService")
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService<ModelCertificate> {

    private final CertificateRepositoryImpl repository;
    private final CertificateValidator certificateValidator;
    private final TagRepositoryImpl tagRepository;
    private final ModelCertificateReadMapper readMapper;
    private final CreateCertificateFromOnlyCertificateMapper certificateMapper;
    private final LanguageMassage languageMassage;

    @Override
    @Transactional
    public List<ModelCertificate> getAllEntity(int limit, int offset) {
        List<Certificate> list = repository.getAllEntity(limit, offset);
        return readMapper.buildListModelCertificates(list);

    }

    @Override
    @Transactional
    public void saveEntity(Certificate certificate) {
        if (certificateValidator.isValid(certificate)) {
            if (certificate.getCreateDate() == null) {
                certificate.setCreateDate(LocalDateTime.now());
            }
            if (certificate.getLastUpdateDate() == null) {
                certificate.setLastUpdateDate(certificate.getCreateDate().plusDays(certificate.getDuration()));
            }
            repository.addEntity(certificate);
            saveCertificatesTag();
        } else {
            throw new IncorrectDataException(languageMassage.getMessage("message.not.valid"));
        }
    }

    @Override
    @Transactional
    public void updateEntity(long id, OnlyCertificate onlyCertificate) {
        Optional<Certificate> c = repository.getEntityById(id);
        if (c.isPresent()) {
            onlyCertificate.setId(id);
            if (onlyCertificate.getCertificateName() == null)
                onlyCertificate.setCertificateName(c.get().getCertificateName());
            if (onlyCertificate.getDescription() == null)
                onlyCertificate.setDescription(c.get().getDescription());
            if (onlyCertificate.getPrice() <= 0)
                onlyCertificate.setPrice(c.get().getPrice());
            if (onlyCertificate.getDuration() <= 0) {
                onlyCertificate.setDuration(c.get().getDuration());
                onlyCertificate.setLastUpdateDate(LocalDateTime.now().plusDays(c.get().getDuration()));
            } else {
                onlyCertificate.setLastUpdateDate(LocalDateTime.now().plusDays(onlyCertificate.getDuration()));
            }
            if (onlyCertificate.getCreateDate() == null)
                onlyCertificate.setCreateDate(c.get().getCreateDate());
            if (onlyCertificate.getLastUpdateDate() == null)
                onlyCertificate.setLastUpdateDate(c.get().getLastUpdateDate());
            Certificate certificate = certificateMapper.mapFrom(onlyCertificate);


            if (certificateValidator.isValid(certificate)) {
                repository.updateEntity(certificate);
                saveCertificatesTag();
            } else {
                throw new IncorrectDataException(languageMassage.getMessage("message.not.valid"));
            }
        } else {
            throw new NoSuchEntityException(languageMassage.getMessage("message.certificate.with.id"));
        }
    }

    @Override
    @Transactional
    public Optional<ModelCertificate> getEntity(long id) {
        Optional<Certificate> c = repository.getEntityById(id);
        if (c.isEmpty()) {
            throw new NoSuchEntityException(languageMassage.getMessage("message.certificate.with.id"));
        }
        return c.map(readMapper::mapFrom);
    }

    @Override
    @Transactional
    public void deleteEntity(long id) {
        Optional<Certificate> c = repository.getEntityById(id);
        if (c.isPresent()) {
            repository.deleteEntity(c.get());
        } else {
            throw new NoSuchEntityException(languageMassage.getMessage("message.certificate.with.id"));
        }
    }

    @Override
    @Transactional
    public int countAllCertificates() {
        return repository.countAllCertificates();
    }


    @Transactional
    public void saveCertificatesTag() {

        List<Tag> tagList = tagRepository.getOnlyTags();
        for (Tag t : tagList) {
            List<Certificate> certificateList = repository.getCertificatesByName(t.getTagName());
            if (!certificateList.isEmpty()) {
                for (Certificate c : certificateList) {
                    if (repository.getCertificateAndTag(c, t).isEmpty()) {
                        repository.saveCertificatesTag(c.getId(), t.getId());
                    }
                }
            }
        }
    }

    @Transactional
    public List<ModelCertificate> getCertificatesByTag(String tagName) {
        List<Certificate> list;
        Optional<Tag> tag = tagRepository.getTagByName(tagName);
        if (tag.isEmpty()) {
            throw new NoSuchEntityException(languageMassage.getMessage("message.be.empty"));
        } else {
            list = repository.getCertificatesByName(tagName);
        }
        return readMapper.buildListModelCertificates(list);
    }

    @Transactional
    public List<ModelCertificate> getCertificateByName(String name) {
        List<Certificate> list = repository.getCertificatesByName(name);
        if (list.isEmpty()) {
            throw new NoSuchEntityException(languageMassage.getMessage("message.with.name"));
        }
        return readMapper.buildListModelCertificates(list);
    }

    @Transactional
    public List<ModelCertificate> getCertificatesByTags(String tagName1, String tagName2) {
        Optional<Tag> tag1 = tagRepository.getTagByName(tagName1);
        Optional<Tag> tag2 = tagRepository.getTagByName(tagName2);
        if (tag1.isEmpty() || tag2.isEmpty()) {
            throw new NoSuchEntityException(languageMassage.getMessage("message.with.name"));
        }

        return readMapper.buildListModelCertificates(repository.getCertificatesByTags(tag1.get().getId(), tag2.get().getId()));
    }
}
