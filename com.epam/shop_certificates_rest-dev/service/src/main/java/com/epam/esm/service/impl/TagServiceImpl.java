package com.epam.esm.service.impl;

import com.epam.esm.Dto.tagDto.CreateTag;
import com.epam.esm.Dto.tagDto.ReadTag;
import com.epam.esm.Dto.tagDto.TagDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectDataException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.mapper.impl.tagMapper.TransitionReadTagFromTag;
import com.epam.esm.mapper.impl.tagMapper.TransitionTagDtoFromTag;
import com.epam.esm.mapper.impl.tagMapper.TransitionTagFromCreateTag;
import com.epam.esm.mapper.impl.tagMapper.TransitionTagFromTagDto;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
//import com.epam.esm.repository.impl.CertificateRepositoryImpl;
//import com.epam.esm.repository.impl.TagRepositoryImpl;
import com.epam.esm.service.TagService;
import com.epam.esm.util.validator.impl.TagsValidator;
import com.epam.esm.util.messange.LanguageMassage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("tagService")
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository repository;
    private final TagsValidator tagsValidator;
//    private final CertificateServiceImpl certificateServiceImpl;
//    private final CertificateRepository certificateRepository;
    private final TransitionReadTagFromTag readMapper;
    private final TransitionTagDtoFromTag tagDtoFromTag;
    private final LanguageMassage languageMassage;
    private final TransitionTagFromCreateTag createTagMapper;
    private final TransitionTagFromTagDto tagFromTagDto;


    @Transactional
    @Override
    public List<ReadTag> getAllEntity(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Tag> tags = repository.findAll(pageable).getContent();
        return readMapper.buildListTag(tags);
    }


    @Transactional
    @Override
    public List<TagDto> getAllTag(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Tag> tags = repository.findAll(pageable).getContent();
        return tagDtoFromTag.buildListOnlyTag(tags);
    }

    @Override
    @Transactional
    public void saveEntity(CreateTag createTag) {
        Tag tag = createTagMapper.mapFrom(createTag);
        if (tagsValidator.isValid(tag)) {
            repository.save(tag);
        } else {
            throw new IncorrectDataException("message.not.valid");
        }
//        certificateServiceImpl.autoSaveCertificatesTag();
    }

    @Override
    @Transactional
    public void updateEntity(long id, TagDto tagDto) {
        Optional<Tag> tag = repository.findById(id);
        if (tag.isPresent()) {
            tag.get().setTagName(tagDto.getTagName());
            if (tagsValidator.isValidModel(tagFromTagDto.mapFrom(tagDto))) {
                repository.save(tag.get());
            } else {
                throw new IncorrectDataException(languageMassage.getMessage("message.not.valid"));
            }
        } else {
            throw new NoSuchEntityException(languageMassage.getMessage("message.tag.with.id"));

        }
//        certificateServiceImpl.autoSaveCertificatesTag();
    }


    @Override
    @Transactional
    public List<TagDto> listTags() {
        List<Tag> tags = repository.findAll();
        return tagDtoFromTag.buildListOnlyTag(tags);
    }

    @Override
    @Transactional
    public Long countAll() {
        return repository.count();
    }

    @Override
    @Transactional
    public ReadTag findById(long id) {
        Optional<Tag> tag = repository.findById(id);
        if (tag.isEmpty()) {
            throw new NoSuchEntityException(languageMassage.getMessage("message.tag.with.id"));
        }
        return readMapper.mapFrom(tag.get());
    }

    @Override
    @Transactional
    public void deleteEntity(long id) {
        if (repository.getReferenceById(id)!=null) {
            repository.deleteById(id);
        } else {
            throw new NoSuchEntityException(languageMassage.getMessage("message.tag.with.id"));
        }
    }

    @Transactional
    @Override
    public ReadTag getPopularTagWithUser() {
//        return readMapper.mapFrom(repository.getPopularTagWithUser());
        return null;
    }


    @Override
    @Transactional
    public void addTagToCertificate(long tId, long cId) {
//        Certificate certificate = certificateRepository.getReferenceById(cId);

    }
}
