package com.epam.esm.repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EntityRepository<T> {

    public Page<T> getAllEntity(Pageable pageable);

    public Optional<T> getEntity(long id);

    public void addEntity(T t);

    public void deleteEntity(long id);
}
