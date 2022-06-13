package com.epam.esm.servises;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService extends EntityService<Tag> {
    @Override
    List<Tag> getAllEntity();

    @Override
    void saveEntity(Tag tag);

    @Override
    void updateEntity(long id, Tag tag);

    @Override
    Optional<Tag> getEntity(long id);

    @Override
    void deleteEntity(long id);
}
