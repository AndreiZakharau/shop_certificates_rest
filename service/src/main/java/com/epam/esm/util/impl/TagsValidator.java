package com.epam.esm.util.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.util.Validator;
import org.springframework.stereotype.Service;

@Service("tagsValidator")
public class TagsValidator implements Validator<Tag> {

    private static final String TEXT_PATTERN = "[^\s]+[A-zА-я0-9-\\s'\"-.]*";

    @Override
    public boolean isValid(Tag tag) {
        return isNameValid(tag.getTagName());
    }

    public boolean isNameValid(String name) {
        if (name == null) {
            return false;
        } else
            return name.length() >= 2 && name.length() <= 60 && name.matches(TEXT_PATTERN);
    }
}
