package com.epam.esm.servises.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repositorys.impl.TagDao;
import com.epam.esm.servises.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagService implements EntityService<Tag> {

    @Autowired
    private TagDao tagDao;

    @Override
    @Transactional
    public List<Tag> getAllEntity() {
        return tagDao.getAll();
    }

    @Override
    @Transactional
    public void saveEntity(Tag tag) {
        tagDao.save(tag);
    }

    @Override
    @Transactional
    public Tag getEntity(long id) {
        return tagDao.get(id);
    }

    @Override
    @Transactional
    public void deleteEntity(long id) {
        tagDao.delete(id);
    }
}
