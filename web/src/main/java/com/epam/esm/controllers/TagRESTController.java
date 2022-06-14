package com.epam.esm.controllers;

import com.epam.esm.entity.Tag;
import com.epam.esm.servises.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1.1")
public class TagRESTController {

    private final TagServiceImpl service;

    @Autowired
    public TagRESTController(TagServiceImpl service) {
        this.service = service;
    }


    /**
     * Created new tag
     * @param tag the tag
     * @return new tag
     */
    @PostMapping("/tags")
    @ResponseStatus(HttpStatus.CREATED)
    public Tag addTag(@RequestBody Tag tag) {
        service.saveEntity(tag);
        return tag;
    }

    /**
     * @return list tags
     */
    @GetMapping("/tags")
    @ResponseStatus(HttpStatus.OK)
    public Page<Tag> listAllTags(@PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable) {
        return service.getAllEntity(pageable);
    }

    /**
     * Get tag by id
     * @param id the id
     * @return tag
     */
    @GetMapping("/tags/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Tag> getTag(@PathVariable long id) {
        return service.getEntity(id);
    }

    /**
     * update tag by id
     * @param tag the tag
     * @param id the id
     * @return the exposed tag
     */
    @PatchMapping("/tags/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tag updateTag(@RequestBody Tag tag, @PathVariable long id) {
        service.updateEntity(id, tag);
        return tag;
    }

    /**
     * delete tag by id
     * @param id the id
     * @return string response
     */
    @DeleteMapping("/tags/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteTag(@PathVariable long id) {
         service.deleteEntity(id);
        return "Tag with ID = " + id + " was deleted.";
    }
}
