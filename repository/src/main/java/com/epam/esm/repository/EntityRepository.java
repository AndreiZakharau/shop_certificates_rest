package com.epam.esm.repository;

import java.util.List;
import java.util.Optional;

public interface EntityRepository<T>{

     List<T> getAllEntity(int limit, int offset);

     Optional<T> getEntityById(long id);

     void addEntity(T t);

     void updateEntity(T t);

//     void deleteEntity(T t);

}
