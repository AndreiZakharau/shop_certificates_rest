package com.epam.esm.service;

import com.epam.esm.Dto.tagDto.CreateTag;
import com.epam.esm.Dto.tagDto.TagDto;
import com.epam.esm.Dto.tagDto.ReadTag;

import java.util.List;

public interface TagService extends EntityService<ReadTag, CreateTag,TagDto> {

    @Override
    List<ReadTag> getAllEntity(int limit, int offset);

    List<TagDto> getAllTag(int limit, int offset);

    @Override
    ReadTag findById(long id);

    @Override
    void deleteEntity(long id);

    @Override
    void saveEntity(CreateTag createTag);

    @Override
    void updateEntity(long id, TagDto tagDto);

    List<TagDto> listTags();

    @Override
    Long countAll();

    ReadTag getPopularTagWithUser();

    void addTagToCertificate(long tId, long cId);

}
