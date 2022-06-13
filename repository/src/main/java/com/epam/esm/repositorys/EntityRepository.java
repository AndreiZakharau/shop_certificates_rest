package com.epam.esm.repositorys;

import java.util.List;
import java.util.Optional;

public interface EntityRepository<T> {

    public List<T> getAllEntity();

    public Optional<T> getEntity(long id);

    public void addEntity(T t);

    public void deleteEntity(long id);
}
