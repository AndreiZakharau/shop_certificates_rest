package com.epam.esm.mapper.impl.tagMapper;

import com.epam.esm.entitys.Tag;
import com.epam.esm.mapper.Mapper;
import com.epam.esm.models.tags.OnlyTag;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateTagFromOnlyTagMapper implements Mapper<OnlyTag, Tag> {
    @Override
    public Tag mapFrom(OnlyTag object) {
        return Tag.builder()
                .id(object.getId())
                .tagName(object.getTagName())
                .build();
    }

    public List<Tag> buildListTags(List<OnlyTag> onlyTags) {
        List<Tag> tags = new ArrayList<>();
        for (OnlyTag onlyTag : onlyTags) {
            Tag tag = Tag.builder()
                    .id(onlyTag.getId())
                    .tagName(onlyTag.getTagName())
                    .build();
//                    new Tag ();
//            tag.setId(tag.getId());
//            tag.setTagName(tag.getTagName());
        }
        return tags;
    }

}
