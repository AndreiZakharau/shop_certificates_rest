package com.epam.esm.servises;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EntityService<T>{

    public Page<T> getAllEntity(Pageable pageable);

    public void saveEntity(T t);

    public void updateEntity(long id, T t);

    public Optional<T> getEntity(long id);

    public void deleteEntity(long id);
}
