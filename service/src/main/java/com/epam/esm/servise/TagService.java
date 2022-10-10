package com.epam.esm.servise;

import com.epam.esm.entity.Tag;
import com.epam.esm.model.tag.OnlyTag;
import com.epam.esm.model.tag.TagModel;

import java.util.List;
import java.util.Optional;

public interface TagService<T> {

    List<T> getAllEntity(int limit, int offset);

    Optional<TagModel> findById(long id);

    void deleteEntity(long id);

    void saveEntity(Tag tag);

    void updateEntity(long id, T t);

    List<OnlyTag> listOnlyTags();

    int countAllTags();

}
