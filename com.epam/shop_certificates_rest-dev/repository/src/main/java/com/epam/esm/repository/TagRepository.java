package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {

    Tag getTagByTagName(String name);


    //    void deleteEntity(long id);
//
//
//    List<Tag> getTags();
//
//    int countAllTags();
//
//    Optional<Tag> getTagByName(String string);
//
//    Tag getPopularTagWithUser();

}
