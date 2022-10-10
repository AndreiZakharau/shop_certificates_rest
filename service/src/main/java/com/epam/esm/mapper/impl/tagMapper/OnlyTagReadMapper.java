package com.epam.esm.mapper.impl.tagMapper;

import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.Dto.tagDto.CreateTag;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OnlyTagReadMapper implements Mapper<Tag, CreateTag> {
    @Override
    public CreateTag mapFrom(Tag object) {
        return new CreateTag(
                object.getId(),
                object.getTagName()
        );
    }

    public List<CreateTag> buildListOnlyTag(List<Tag> tags) {
        List<CreateTag> tagList = new ArrayList<>();
        for (Tag tag : tags) {
            CreateTag createTag = CreateTag.builder()
                    .id(tag.getId())
                    .tagName(tag.getTagName())
                    .build();
            tagList.add(createTag);
        }
        return tagList;
    }
}
