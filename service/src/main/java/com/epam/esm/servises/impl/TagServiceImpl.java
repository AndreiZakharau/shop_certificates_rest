package com.epam.esm.servises.impl;

import com.epam.esm.entitys.Tag;
import com.epam.esm.exceptions.IncorrectDataException;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.mapper.impl.tagMapper.OnlyTagMapper;
import com.epam.esm.mapper.impl.tagMapper.TagModelMapper;
import com.epam.esm.mapper.impl.tagMapper.TagModelReadMapper;
import com.epam.esm.models.tags.OnlyTag;
import com.epam.esm.models.tags.TagModel;
import com.epam.esm.repositorys.impl.TagRepositoryImpl;
import com.epam.esm.servises.TagService;
import com.epam.esm.util.impl.TagsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("tagService")
@RequiredArgsConstructor
public class TagServiceImpl implements TagService<TagModel> {


    private final TagRepositoryImpl repository;
    private final TagsValidator tagsValidator;
//    private final CertificateServiceImpl certificateServiceImpl;
    private final TagModelMapper tagModelMapper;
    private final TagModelReadMapper readMapper;
    private final OnlyTagMapper onlyTagMapper;


    @Transactional
    public List<TagModel> getAllEntity(int limit, int offset) {
        List<Tag> tags = repository.getAllEntity(limit, offset);
        return readMapper.buildListTag(tags);
    }

    @Override
    @Transactional
    public void saveEntity(Tag tag) {
        if(tagsValidator.isValid(tag)) {
            repository.addEntity(tag);
        }else {
            throw new IncorrectDataException("message.not.valid");
        }
//        certificateServiceImpl.saveCertificatesTag();
    }

    @Override
    @Transactional
    public void updateEntity(long id, TagModel tagModel){
        Optional<Tag> tag = repository.getEntity(id);
        if (tag.isPresent()){
            tagModel.setId(tagModel.getId());
           if (tagsValidator.isValidModel(tagModel)){
              repository.updateEntity(tagModelMapper.mapFrom(tagModel));
           }else{
                throw new IncorrectDataException("message.not.valid");
           }
        }else {
            return ; //TODO (бросить исключение, что токого id нет)

        }
//        certificateServiceImpl.saveCertificatesTag();
    }

    @Override
    @Transactional
    public List<OnlyTag> listOnlyTags() {
        List<Tag> tags = repository.getOnlyTags();
        return onlyTagMapper.buildListOnlyTag(tags);
    }

    @Override
    @Transactional
    public int countAllTags() {
        return repository.countAllTags();
    }

    @Override
    @Transactional
    public Optional<TagModel> getEntity(long id) {
        Optional<Tag> tag = Optional.ofNullable(repository.getEntity(id)).orElseThrow();
        if(tag.isEmpty()){
            throw new NoSuchEntityException("message.tag.with.id");
        }
        return tag.map(readMapper::mapFrom);
    }

    @Override
    @Transactional
    public void deleteEntity(long id) {
        if(repository.getEntity(id).isPresent()){
            repository.deleteEntity(id);
        }else {
                    throw new NoSuchEntityException("message.tag.with.id");
        }
    }

    


}
