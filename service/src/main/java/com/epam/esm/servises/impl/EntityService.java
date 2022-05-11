package com.epam.esm.servises.impl;

import java.util.List;

public interface EntityService<T> {

    public List<T> getAll();

    public void save(T t);

    public T get(long id);

    public void delete(long id);
}
