package com.epam.esm.servises;

import java.util.List;
import java.util.Optional;

public interface EntityService<T>{

    public List<T> getAllEntity();

    public void saveEntity(T t);

    public void updateEntity(long id, T t);

    public Optional<T> getEntity(long id);

    public void deleteEntity(long id);
}
