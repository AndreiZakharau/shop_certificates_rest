package com.epam.esm.mapper.impl.tagMapper;

import com.epam.esm.entitys.Tag;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.models.tags.OnlyTag;
import org.springframework.stereotype.Service;

@Service
public class OnlyTagReadMapper implements Mapper<Tag, OnlyTag> {
    @Override
    public OnlyTag mapFrom(Tag object) {

        return new OnlyTag(
                object.getId(),
                object.getTagName()
        );
    }
}
