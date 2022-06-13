package com.epam.esm.servises.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repositorys.impl.TagRepositoryImpl;
import com.epam.esm.servises.EntityService;
import com.epam.esm.util.impl.TagsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("tagService")
public class TagServiceImpl implements EntityService<Tag> {

    @Autowired
    private TagRepositoryImpl repository;
    @Autowired
    private TagsValidator tagsValidator;
    @Autowired
    private CertificateServiceImpl certificateServiceImpl;

    public TagServiceImpl(TagRepositoryImpl repository, TagsValidator tagsValidator, CertificateServiceImpl certificateServiceImpl) {
        this.repository = repository;
        this.tagsValidator = tagsValidator;
        this.certificateServiceImpl = certificateServiceImpl;
    }

    @Override
    @Transactional
    public List<Tag> getAllEntity() {
        return repository.getAllEntity();
    }

    @Override
    @Transactional
    public void saveEntity(Tag tag) {
        if(tagsValidator.isValid(tag)) {
            repository.addEntity(tag);
        }else {
            //TODO
//            throw new IncorrectDataException("message.not.valid");
        }
        certificateServiceImpl.saveCertificatesTag();
    }

    @Override
    @Transactional
    public void updateEntity(long id, Tag tag){
        if ((repository.getEntity(id)).isPresent()){
           if (tagsValidator.isValid(tag)){
               repository.addEntity(tag);
           }else{
               //TODO throw new IncorrectDataException("message.not.valid");
           }
        }else {
            return ; //TODO (бросить исключение, что токого id нет)

        }
        certificateServiceImpl.saveCertificatesTag();
    }

    @Override
    @Transactional
    public Optional<Tag> getEntity(long id) {
        Optional<Tag> tag = repository.getEntity(id);
        if(tag.isEmpty()){
//            throw new NoSuchEntityException("message.tag.with.id");
        }
        return tag;
    }

    @Override
    @Transactional
    public void deleteEntity(long id) {
        if(repository.getEntity(id).isPresent()){
            repository.deleteEntity(id);
        }else {
            // TODO           throw new NoSuchEntityException("message.tag.with.id");
        }
    }
}
