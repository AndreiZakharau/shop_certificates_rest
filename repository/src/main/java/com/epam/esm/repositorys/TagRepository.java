package com.epam.esm.repositorys;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends EntityRepository<Tag> {
    @Override
    List<Tag> getAllEntity();

    @Override
    Optional<Tag> getEntity(long id);

    @Override
    void addEntity(Tag tag);

    @Override
    void deleteEntity(long id);
}
