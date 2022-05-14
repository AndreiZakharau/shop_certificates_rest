package com.epam.esm.servises;

import java.util.List;

public interface EntityService<T>{

    public List<T> getAllEntity();

    public void saveEntity(T t);

    public void updateEntity(long id, T t);

    public T getEntity(long id);

    public void deleteEntity(long id);
}
