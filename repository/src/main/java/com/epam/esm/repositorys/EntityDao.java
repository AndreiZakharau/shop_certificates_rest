package com.epam.esm.repositorys;

import java.util.List;

public interface EntityDao<T> {

    public List<T> getAllEntity();

    public T getEntity(long id);

    public void addEntity(T t);

    public void updateEntity(long id,T t);

    public void deleteEntity(long id);
}
