package com.epam.esm.service.impl;

import com.epam.esm.Dto.tagDto.CreateTag;
import com.epam.esm.Dto.tagDto.ReadTag;
import com.epam.esm.Dto.tagDto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectDataException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.mapper.impl.tagMapper.TransitionReadTagFromTag;
import com.epam.esm.mapper.impl.tagMapper.TransitionTagDtoFromTag;
import com.epam.esm.mapper.impl.tagMapper.TransitionTagFromCreateTag;
import com.epam.esm.mapper.impl.tagMapper.TransitionTagFromTagDto;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.TagService;
import com.epam.esm.util.validator.impl.TagsValidator;
import com.epam.esm.util.messange.LanguageMassage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("tagService")
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {


    private final TagRepositoryImpl repository;
    private final TagsValidator tagsValidator;
    private final CertificateServiceImpl certificateServiceImpl;
    private final TransitionReadTagFromTag readMapper;
    private final TransitionTagDtoFromTag tagDtoFromTag;
    private final LanguageMassage languageMassage;
    private final TransitionTagFromCreateTag createTagMapper;
    private final TransitionTagFromTagDto tagFromTagDto;


    @Transactional
    @Override
    public List<ReadTag> getAllEntity(int limit, int offset) {
        List<Tag> tags = repository.getAllEntity(limit, offset);
        return readMapper.buildListTag(tags);
    }


    @Transactional
    @Override
    public List<TagDto> getAllTag(int limit, int offset) {
        List<Tag> tags = repository.getAllEntity(limit, offset);
        return tagDtoFromTag.buildListOnlyTag(tags);
    }

    @Override
    @Transactional
    public void saveEntity(CreateTag createTag) {
        Tag tag = createTagMapper.mapFrom(createTag);
        if (tagsValidator.isValid(tag)) {
            repository.addEntity(tag);
        } else {
            throw new IncorrectDataException("message.not.valid");
        }
        certificateServiceImpl.saveCertificatesTag();
    }

    @Override
    @Transactional
    public void updateEntity(long id, TagDto tagDto) {
        Optional<Tag> tag = repository.getEntityById(id);
        if (tag.isPresent()) {
            tag.get().setTagName(tagDto.getTagName());
            if (tagsValidator.isValidModel(tagFromTagDto.mapFrom(tagDto))) {
                repository.updateEntity(tag.get());
            } else {
                throw new IncorrectDataException(languageMassage.getMessage("message.not.valid"));
            }
        } else {
            throw new NoSuchEntityException(languageMassage.getMessage("message.tag.with.id"));

        }
        certificateServiceImpl.saveCertificatesTag();
    }


    @Override
    @Transactional
    public List<TagDto> listTags() {
        List<Tag> tags = repository.getTags();
        return tagDtoFromTag.buildListOnlyTag(tags);
    }

    @Override
    @Transactional
    public int countAll() {
        return repository.countAllTags();
    }

    @Override
    @Transactional
    public Optional<ReadTag> findById(long id) {
        Optional<Tag> tag = Optional.ofNullable(repository.getEntityById(id)).orElseThrow();
        if (tag.isEmpty()) {
            throw new NoSuchEntityException(languageMassage.getMessage("message.tag.with.id"));
        }
        return tag.map(readMapper::mapFrom);
    }

    @Override
    @Transactional
    public void deleteEntity(long id) {
        if (repository.getEntityById(id).isPresent()) {
            repository.deleteEntity(id);
        } else {
            throw new NoSuchEntityException(languageMassage.getMessage("message.tag.with.id"));
        }
    }

    @Transactional
    @Override
    public ReadTag getPopularTagWithUser() {
        return readMapper.mapFrom(repository.getPopularTagWithUser());
    }


}
