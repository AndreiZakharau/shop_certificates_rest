package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.model.tag.OnlyTag;
import com.epam.esm.model.tag.TagModel;
import com.epam.esm.pagination.Pagination;
import com.epam.esm.servise.impl.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.1")

public class TagRESTController {

    private final TagServiceImpl service;

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
     * @return list tag
     */
    @GetMapping("/tags")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<OnlyTag> listOnlyTags(@RequestParam("page") int page,
                                      @RequestParam("size") int size) {
        int offset = Pagination.offset(page, size);
        int totalRecords = service.countAllTags();
        int pages = Pagination.findPages(totalRecords, size);
        int lastPage = Pagination.findLastPage(pages, size, totalRecords);
        Link prevLink = linkTo(methodOn(TagRESTController.class).getAllTags(Pagination.findPrevPage(page), size))
                .withRel("prev");
        Link nextLink = linkTo(methodOn(TagRESTController.class).getAllTags(Pagination.findNextPage(page, lastPage), size))
                .withRel("next");
        List<OnlyTag> models = (service.getAllOnlyTag(size, offset));

        return CollectionModel.of(models, prevLink, nextLink);
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
        Optional<TagModel> model = service.findById(id);
        allTagsLink(Optional.ofNullable(model).get().orElseThrow());
        return model;
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
        return "message.delete.tag";
    }

    /**
     * @param page the page
     * @param size the size
     * @return list tag
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

    /**
     * @return the popular tag from the user
     * with the maximum sum of all order
     */
    @GetMapping("/tags/popular")
    public TagModel getPopularTagWithUser() {
        TagModel tag = service.getPopularTagWithUser();
        allTagsLink(tag);
        return  tag;
    }

    private void allTagsLink(TagModel model) {
        model.add(linkTo(methodOn(TagRESTController.class)
                .getAllTags(1,5))
                .withRel("tag")
                .withType(HttpMethod.GET.name()));
    }

    private void deleteLinksForTags(List<TagModel> models){
        for (TagModel tagModel : models){
            long tagId = tagModel.getId();
            tagModel.add(linkTo(methodOn(TagRESTController.class)
                    .deleteTag(tagId))
                    .withRel("delete_tag")
                    .withType(HttpMethod.DELETE.name()));
        }
    }

}
