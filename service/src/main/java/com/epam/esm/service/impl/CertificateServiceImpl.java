package com.epam.esm.service.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectDataException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.mapper.impl.certificateMapper.CreateCertificateFromOnlyCertificateMapper;
import com.epam.esm.mapper.impl.certificateMapper.ModelCertificateReadMapper;
import com.epam.esm.Dto.certificateDto.ReadCertificate;
import com.epam.esm.Dto.certificateDto.CreateCertificate;
import com.epam.esm.repository.impl.CertificateRepositoryImpl;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.CertificateService;
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
public class CertificateServiceImpl implements CertificateService<ReadCertificate> {

    private final CertificateRepositoryImpl repository;
    private final CertificateValidator certificateValidator;
    private final TagRepositoryImpl tagRepository;
    private final ModelCertificateReadMapper readMapper;
    private final CreateCertificateFromOnlyCertificateMapper certificateMapper;
    private final LanguageMassage languageMassage;

    @Override
    @Transactional
    public List<ReadCertificate> getAllEntity(int limit, int offset) {
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
    public void updateEntity(long id, CreateCertificate createCertificate) {
        Optional<Certificate> c = repository.getEntityById(id);
        if (c.isPresent()) {
            createCertificate.setId(id);
            if (createCertificate.getCertificateName() == null)
                createCertificate.setCertificateName(c.get().getCertificateName());
            if (createCertificate.getDescription() == null)
                createCertificate.setDescription(c.get().getDescription());
            if (createCertificate.getPrice() <= 0)
                createCertificate.setPrice(c.get().getPrice());
            if (createCertificate.getDuration() <= 0) {
                createCertificate.setDuration(c.get().getDuration());
                createCertificate.setLastUpdateDate(LocalDateTime.now().plusDays(c.get().getDuration()));
            } else {
                createCertificate.setLastUpdateDate(LocalDateTime.now().plusDays(createCertificate.getDuration()));
            }
            if (createCertificate.getCreateDate() == null)
                createCertificate.setCreateDate(c.get().getCreateDate());
            if (createCertificate.getLastUpdateDate() == null)
                createCertificate.setLastUpdateDate(c.get().getLastUpdateDate());
            Certificate certificate = certificateMapper.mapFrom(createCertificate);


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
    public Optional<ReadCertificate> getEntity(long id) {
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

        List<Tag> tagList = tagRepository.getTags();
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
    public List<ReadCertificate> getCertificatesByTag(String tagName) {
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
    public List<ReadCertificate> getCertificateByName(String name) {
        List<Certificate> list = repository.getCertificatesByName(name);
        if (list.isEmpty()) {
            throw new NoSuchEntityException(languageMassage.getMessage("message.with.name"));
        }
        return readMapper.buildListModelCertificates(list);
    }

    @Transactional
    public List<ReadCertificate> getCertificatesByTags(String tagName1, String tagName2) {
        Optional<Tag> tag1 = tagRepository.getTagByName(tagName1);
        Optional<Tag> tag2 = tagRepository.getTagByName(tagName2);
        if (tag1.isEmpty() || tag2.isEmpty()) {
            throw new NoSuchEntityException(languageMassage.getMessage("message.with.name"));
        }

        return readMapper.buildListModelCertificates(repository.getCertificatesByTags(tag1.get().getId(), tag2.get().getId()));
    }
}
