package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.Dto.tagDto.CreateTag;
import com.epam.esm.Dto.tagDto.ReadTag;

import java.util.List;
import java.util.Optional;

public interface TagService<T> {

    List<T> getAllEntity(int limit, int offset);

    Optional<ReadTag> findById(long id);

    void deleteEntity(long id);

    void saveEntity(Tag tag);

    void updateEntity(long id, T t);

    List<CreateTag> listOnlyTags();

    int countAllTags();

}
