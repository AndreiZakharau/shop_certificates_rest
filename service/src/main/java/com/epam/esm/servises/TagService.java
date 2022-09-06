package com.epam.esm.servises;

import com.epam.esm.entitys.Tag;
import com.epam.esm.models.tags.OnlyTag;
import com.epam.esm.models.tags.TagModel;

import java.util.List;
import java.util.Optional;

public interface TagService <T> {

    List<T> getAllEntity(int limit, int offset);

    Optional<TagModel> findById(long id);

    void deleteEntity(long id);

    void saveEntity(Tag tag);

    void updateEntity(long id, T t);

    List<OnlyTag> listOnlyTags();

    int countAllTags();

}
