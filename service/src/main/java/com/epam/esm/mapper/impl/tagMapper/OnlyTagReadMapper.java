package com.epam.esm.mapper.impl.tagMapper;

import com.epam.esm.entitys.Tag;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.models.tags.OnlyTag;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OnlyTagReadMapper implements Mapper<Tag, OnlyTag> {
    @Override
    public OnlyTag mapFrom(Tag object) {
        return new OnlyTag(
                object.getId(),
                object.getTagName()
        );
    }
    public List<OnlyTag> buildListOnlyTag(List<Tag> tags) {
        List<OnlyTag> tagList = new ArrayList<>();
        for (Tag tag : tags) {
            OnlyTag onlyTag = OnlyTag.builder()
                    .id(tag.getId())
                    .tagName(tag.getTagName())
                    .build();
//                    new OnlyTag ();
//            onlyTag.setId(tag.getId());
//            onlyTag.setTagName(tag.getTagName());
            tagList.add(onlyTag);
        }
        return tagList;
    }
}
