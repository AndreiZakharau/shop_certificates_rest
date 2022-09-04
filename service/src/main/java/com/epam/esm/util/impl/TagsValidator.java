package com.epam.esm.util.impl;

import com.epam.esm.entitys.Tag;
import com.epam.esm.models.tags.TagModel;
import com.epam.esm.util.Validator;
import org.springframework.stereotype.Service;

@Service("tagsValidator")
public class TagsValidator implements Validator<Tag> {

    private static final String TEXT_PATTERN = "[^\s]+[A-Za-z0-9-\s'\"-.]*"; //[^s]+[A-Za-zА-Яа-я0-9-s'"-.]* посде добовления русского

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

    public boolean isValidModel(TagModel tag) {
        return isNameValid(tag.getTagName());
    }

    public boolean isNameValidModel(String name) {
        if (name == null) {
            return false;
        } else
            return name.length() >= 2 && name.length() <= 60 && name.matches(TEXT_PATTERN);
    }
}
