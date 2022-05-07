package com.epam.esm.repositorys;

import java.util.List;

public interface EntityDao<T> {

    public List<T> getAll();

    public void save(T t);

    public T get(long id);

    public void delete(long id);
}
