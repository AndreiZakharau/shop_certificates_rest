package com.epam.esm.repositorys;

import com.epam.esm.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TagRepository extends EntityRepository<Tag> {
    @Override
    Page<Tag> getAllEntity(Pageable pageable);

    @Override
    Optional<Tag> getEntity(long id);

    @Override
    void addEntity(Tag tag);

    @Override
    void deleteEntity(long id);
}
