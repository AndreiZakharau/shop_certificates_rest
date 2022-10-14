package com.epam.esm.service;

import com.epam.esm.Dto.tagDto.CreateTag;
import com.epam.esm.Dto.tagDto.TagDto;
import com.epam.esm.Dto.tagDto.ReadTag;

import java.util.List;
import java.util.Optional;

public interface TagService extends EntityService<ReadTag, CreateTag,TagDto> {

    @Override
    List<ReadTag> getAllEntity(int limit, int offset);

    List<TagDto> getAllTag(int limit, int offset);

    @Override
    Optional<ReadTag> findById(long id);

    @Override
    void deleteEntity(long id);

    @Override
    void saveEntity(CreateTag createTag);

    @Override
    void updateEntity(long id, TagDto tagDto);

    List<TagDto> listTags();

    @Override
    int countAll();

    public ReadTag getPopularTagWithUser();

}
