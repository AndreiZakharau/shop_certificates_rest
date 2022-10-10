package com.epam.esm.mapper.impl.tagMapper;

import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.Dto.tagDto.CreateTag;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateTagFromOnlyTagMapper implements Mapper<CreateTag, Tag> {
    @Override
    public Tag mapFrom(CreateTag object) {
        return Tag.builder()
                .id(object.getId())
                .tagName(object.getTagName())
                .build();
    }

    public List<Tag> buildListTags(List<CreateTag> createTags) {
        List<Tag> tags = new ArrayList<>();
        for (CreateTag createTag : createTags) {
            Tag tag = Tag.builder()
                    .id(createTag.getId())
                    .tagName(createTag.getTagName())
                    .build();
        }
        return tags;
    }

}
