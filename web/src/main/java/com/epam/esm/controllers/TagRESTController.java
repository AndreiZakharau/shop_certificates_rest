package com.epam.esm.controllers;

import com.epam.esm.entity.Tag;
import com.epam.esm.servises.impl.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.1")
public class TagRESTController {

    @Autowired
    private TagService service;

    @PostMapping("/tags")
    public Tag addTag(@RequestBody Tag tag){
        service.saveEntity(tag);
        return tag;
    }

    @GetMapping("/tags")
    public List<Tag> listAllTags(){
        return service.getAllEntity();
    }

    @GetMapping("/tags/{id}")
    public Tag getTag(@PathVariable long id) {
        return service.getEntity(id);
    }

    @PutMapping("/tags")
    public Tag updateTag(@RequestBody Tag tag){
        service.saveEntity(tag);
        return tag;
    }

    @DeleteMapping("/tag/{id}")
    public String deleteTag(@PathVariable long id){
        service.deleteEntity(id);
        return "Tag with ID = " + id + " was deleted.";
    }
}
