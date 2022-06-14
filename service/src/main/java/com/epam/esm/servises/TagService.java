package com.epam.esm.servises;

import com.epam.esm.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TagService extends EntityService<Tag> {
    @Override
    Page<Tag> getAllEntity(Pageable pageable);

    @Override
    void saveEntity(Tag tag);

    @Override
    void updateEntity(long id, Tag tag);

    @Override
    Optional<Tag> getEntity(long id);

    @Override
    void deleteEntity(long id);
}
