package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends EntityRepository<Tag> {

    void deleteEntity(long id);


    List<Tag> getTags();

    int countAllTags();

    Optional<Tag> getTagByName(String string);

    Tag getPopularTagWithUser();

}
