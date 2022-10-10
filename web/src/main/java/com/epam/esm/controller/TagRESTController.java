package com.epam.esm.controller;

import com.epam.esm.entity.Tag;
import com.epam.esm.Dto.tagDto.CreateTag;
import com.epam.esm.Dto.tagDto.ReadTag;
import com.epam.esm.pagination.Pagination;
import com.epam.esm.service.impl.TagServiceImpl;
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
     * Created new tagDto
     *
     * @param tag the tagDto
     * @return new tagDto
     */
    @PostMapping("/tags")
    @ResponseStatus(HttpStatus.CREATED)
    public Tag addTag(@RequestBody Tag tag) {
        service.saveEntity(tag);
        return tag;
    }

    /**
     * @return list tagDto
     */
    @GetMapping("/tags")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<CreateTag> listOnlyTags(@RequestParam("page") int page,
                                                   @RequestParam("size") int size) {
        int offset = Pagination.offset(page, size);
        int totalRecords = service.countAllTags();
        int pages = Pagination.findPages(totalRecords, size);
        int lastPage = Pagination.findLastPage(pages, size, totalRecords);
        Link prevLink = linkTo(methodOn(TagRESTController.class).getAllTags(Pagination.findPrevPage(page), size))
                .withRel("prev");
        Link nextLink = linkTo(methodOn(TagRESTController.class).getAllTags(Pagination.findNextPage(page, lastPage), size))
                .withRel("next");
        List<CreateTag> models = (service.getAllOnlyTag(size, offset));

        return CollectionModel.of(models, prevLink, nextLink);
    }

    /**
     * Get tagDto by id
     *
     * @param id the id
     * @return tagDto
     */
    @GetMapping("/tags/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ReadTag> getTag(@PathVariable long id) {
        Optional<ReadTag> model = service.findById(id);
        allTagsLink(Optional.ofNullable(model).get().orElseThrow());
        return model;
    }

    /**
     * update tagDto by id
     *
     * @param tag the tagDto
     * @param id  the id
     * @return the exposed tagDto
     */
    @PatchMapping("/tags/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReadTag updateTag(@RequestBody ReadTag tag, @PathVariable long id) {
        service.updateEntity(id, tag);
        return tag;
    }

    /**
     * delete tagDto by id
     *
     * @param id the id
     * @return string response
     */
    @DeleteMapping("/tags/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteTag(@PathVariable long id) {
        service.deleteEntity(id);
        return "message.delete.tagDto";
    }

    /**
     * @param page the page
     * @param size the size
     * @return list tagDto
     */
    @GetMapping("/tags/all")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ReadTag> getAllTags(@RequestParam("page") int page,
                                               @RequestParam("size") int size) {
        int offset = Pagination.offset(page, size);
        int totalRecords = service.countAllTags();
        int pages = Pagination.findPages(totalRecords, size);
        int lastPage = Pagination.findLastPage(pages, size, totalRecords);
        Link prevLink = linkTo(methodOn(TagRESTController.class).getAllTags(Pagination.findPrevPage(page), size))
                .withRel("prev");
        Link nextLink = linkTo(methodOn(TagRESTController.class).getAllTags(Pagination.findNextPage(page, lastPage), size))
                .withRel("next");
        List<ReadTag> models = service.getAllEntity(size, offset);

        return CollectionModel.of(models, prevLink, nextLink);
    }

    /**
     * @return the popular tagDto from the userDto
     * with the maximum sum of all orderDto
     */
    @GetMapping("/tags/popular")
    public ReadTag getPopularTagWithUser() {
        ReadTag tag = service.getPopularTagWithUser();
        allTagsLink(tag);
        return  tag;
    }

    private void allTagsLink(ReadTag model) {
        model.add(linkTo(methodOn(TagRESTController.class)
                .getAllTags(1,5))
                .withRel("tagDto")
                .withType(HttpMethod.GET.name()));
    }

    private void deleteLinksForTags(List<ReadTag> models){
        for (ReadTag readTag : models){
            long tagId = readTag.getId();
            readTag.add(linkTo(methodOn(TagRESTController.class)
                    .deleteTag(tagId))
                    .withRel("delete_tag")
                    .withType(HttpMethod.DELETE.name()));
        }
    }

}
