package com.epam.esm.service;

import com.epam.esm.Dto.orderDto.ReadOrder;

import java.util.List;

public interface EntityService<T,K,F> {

    List<T> getAllEntity(int limit, int offset);

    void saveEntity(K k);

    void updateEntity(long id, F f);

    T findById(long id);

    void deleteEntity(long id);

    Long countAll();
}