package com.epam.esm.repositorys;

import java.util.List;
import java.util.Optional;

public interface EntityRepository<T>{

    public List<T> getAllEntity(int limit, int offset);

    public Optional<T> getEntityById(long id);

    public void addEntity(T t);

//    public void deleteEntity(long id);

    public void updateEntity(T t);
}
