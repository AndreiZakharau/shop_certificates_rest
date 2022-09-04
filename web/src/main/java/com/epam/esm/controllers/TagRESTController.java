package com.epam.esm.controllers;

import com.epam.esm.entitys.Tag;
import com.epam.esm.mapper.impl.tagMapper.TagModelMapper;
import com.epam.esm.models.tags.OnlyTag;
import com.epam.esm.models.tags.TagModel;
import com.epam.esm.pagination.Pagination;
import com.epam.esm.servises.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController

@RequestMapping("/api/v1.1")
public class TagRESTController {

    private final TagServiceImpl service;
    private final TagModelMapper mapper;
    @Autowired
    public TagRESTController(TagServiceImpl service, TagModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

//    public TagRESTController(TagServiceImpl service, TagModelMapper mapper) {
//        this.service = service;
//        this.mapper = mapper;
//    }
//    @Autowired
//    public TagRESTController(TagServiceImpl service) {
//        this.service = service;
//    }


    /**
     * Created new tag
     *
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
    public List<OnlyTag> listOnlyTags() {
        return service.listOnlyTags();
    }

    /**
     * Get tag by id
     *
     * @param id the id
     * @return tag
     */
    @GetMapping("/tags/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<TagModel> getTag(@PathVariable long id) {
        return service.getEntity(id);
    }

    /**
     * update tag by id
     *
     * @param tag the tag
     * @param id  the id
     * @return the exposed tag
     */
    @PatchMapping("/tags/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagModel updateTag(@RequestBody TagModel tag, @PathVariable long id) {
        service.updateEntity(id, tag);
        return tag;
    }

    /**
     * delete tag by id
     *
     * @param id the id
     * @return string response
     */
    @DeleteMapping("/tags/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteTag(@PathVariable long id) {
        service.deleteEntity(id);
        return "Tag with ID = " + id + " was deleted.";
    }

    /**
     * @param page the page
     * @param size the size
     * @return list tags
     */
    @GetMapping("/tags/all")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<TagModel> getAllTags(@RequestParam("page") int page,
                                     @RequestParam("size") int size) {
        int offset = Pagination.offset(page, size);
        int totalRecords = service.countAllTags();
        int pages = Pagination.findPages(totalRecords, size);
        int lastPage = Pagination.findLastPage(pages, size, totalRecords);
        Link prevLink = linkTo(methodOn(TagRESTController.class).getAllTags(Pagination.findPrevPage(page), size))
                .withRel("prev");
        Link nextLink = linkTo(methodOn(TagRESTController.class).getAllTags(Pagination.findNextPage(page, lastPage), size))
                .withRel("next");
        List<TagModel> models = service.getAllEntity(size, offset);
        return CollectionModel.of(models, prevLink, nextLink);

    }

}
