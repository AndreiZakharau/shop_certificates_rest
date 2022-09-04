package com.epam.esm.repositorys;

import com.epam.esm.entitys.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends EntityRepository<Tag> {
    @Override
    List<Tag> getAllEntity(int limit, int offset);

    @Override
    Optional<Tag> getEntity(long id);

    @Override
    void addEntity(Tag tag);

//    @Override
    void deleteEntity(long id);

    @Override
    void updateEntity(Tag tag);

    List<Tag> getOnlyTags();

}
