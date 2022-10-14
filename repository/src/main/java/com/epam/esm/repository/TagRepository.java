package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends EntityRepository<Tag> {
    @Override
    List<Tag> getAllEntity(int limit, int offset);

    @Override
    Optional<Tag> getEntityById(long id);

    @Override
    void addEntity(Tag tag);

    void deleteEntity(long id);

    @Override
    void updateEntity(Tag tag);

    List<Tag> getTags();

    int countAllTags();

    Optional<Tag> getTagByName(String string);

    Tag getPopularTagWithUser();

}
