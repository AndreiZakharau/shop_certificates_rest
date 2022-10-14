package com.epam.esm.controller;

import com.epam.esm.Dto.tagDto.CreateTag;
import com.epam.esm.Dto.tagDto.TagDto;
import com.epam.esm.Dto.tagDto.ReadTag;
import com.epam.esm.pagination.Pagination;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1.1/tags")

public class TagController {

    @Autowired
    private TagServiceImpl service;

    /**
     * Created new tag
     *
     * @param createTag the createTag(Dto)
     * @return new createTag(Dto)
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateTag addTag(@RequestBody CreateTag createTag) {
        service.saveEntity(createTag);
        return createTag;
    }


    /**
     * @param page the page
     * @param size the size
     * @return List createTag(Dto Tag)
     */
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<TagDto> listTags(@RequestParam("page") int page,
                                            @RequestParam("size") int size) {
        int offset = Pagination.offset(page, size);
        int totalRecords = service.countAll();
        int pages = Pagination.findPages(totalRecords, size);
        int lastPage = Pagination.findLastPage(pages, size, totalRecords);
        Link prevLink = linkTo(methodOn(TagController.class).getAllTags(Pagination.findPrevPage(page), size))
                .withRel("prev");
        Link nextLink = linkTo(methodOn(TagController.class).getAllTags(Pagination.findNextPage(page, lastPage), size))
                .withRel("next");
        List<TagDto> tags = (service.getAllTag(size, offset));

        return CollectionModel.of(tags, prevLink, nextLink);
    }

    /**
     * Get readTag(Dto Tag) by id
     *
     * @param id the id
     * @return readTag(Dto Tag)
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ReadTag> getTag(@PathVariable long id) {
        Optional<ReadTag> model = service.findById(id);
        allTagsLink(Optional.ofNullable(model).get().orElseThrow());
        return model;
    }

    /**
     * update readTag(Dto Tag) by id
     *
     * @param tag the readTag(Dto Tag)
     * @param id  the id
     * @return the exposed readTag(Dto Tag)
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto updateTag(@RequestBody TagDto tag, @PathVariable long id) {
        service.updateEntity(id, tag);
        return tag;
    }

    /**
     * delete tag by id
     *
     * @param id the id
     * @return string response
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteTag(@PathVariable long id) {
        service.deleteEntity(id);
        return "Tag with ID = " + id + " was deleted.";
    }

    /**
     * @param page the page
     * @param size the size
     * @return list readTag(Dto Tag)
     */
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ReadTag> getAllTags(@RequestParam("page") int page,
                                               @RequestParam("size") int size) {
        int offset = Pagination.offset(page, size);
        int totalRecords = service.countAll();
        int pages = Pagination.findPages(totalRecords, size);
        int lastPage = Pagination.findLastPage(pages, size, totalRecords);
        Link prevLink = linkTo(methodOn(TagController.class).getAllTags(Pagination.findPrevPage(page), size))
                .withRel("prev");
        Link nextLink = linkTo(methodOn(TagController.class).getAllTags(Pagination.findNextPage(page, lastPage), size))
                .withRel("next");
        List<ReadTag> models = service.getAllEntity(size, offset);

        return CollectionModel.of(models, prevLink, nextLink);
    }

    /**
     * @return the popular readTag(Dto Tag) from the user
     * with the maximum sum of all order
     */
    @GetMapping("/popular")
    public ReadTag getPopularTagWithUser() {
        ReadTag tag = service.getPopularTagWithUser();
        allTagsLink(tag);
        return  tag;
    }

    private void allTagsLink(ReadTag model) {
        model.add(linkTo(methodOn(TagController.class)
                .getAllTags(1,5))
                .withRel("tags")
                .withType(HttpMethod.GET.name()));
    }

    private void deleteLinksForTags(List<ReadTag> models){
        for (ReadTag readTag : models){
            long tagId = readTag.getId();
            readTag.add(linkTo(methodOn(TagController.class)
                    .deleteTag(tagId))
                    .withRel("delete_tag")
                    .withType(HttpMethod.DELETE.name()));
        }
    }

}
