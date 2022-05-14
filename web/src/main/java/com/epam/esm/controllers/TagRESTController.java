//package com.epam.esm.controllers;
//
//import com.epam.esm.entity.Tag;
//import com.epam.esm.servises.impl.TagService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//@RestController
//@RequestMapping("/api/v1.1")
//public class TagRESTController {
//
//    private final TagService service;
//
//    @Autowired
//    public TagRESTController(TagService service) {
//        this.service = service;
//    }
//
//
//    /**
//     * Created new tag.
//     * @param tag the tag
//     * @return new tag
//     */
//    @PostMapping("/tags")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Tag addTag(@RequestBody Tag tag) {
//        service.saveEntity(tag);
//        return tag;
//    }
//
//    /**
//     * @return list tags
//     */
//    @GetMapping("/tags")
//    @ResponseStatus(HttpStatus.OK)
//    public List<Tag> listAllTags() {
//        return service.getAllEntity();
//    }
//
//    /**
//     * Get tag by id
//     * @param id the id
//     * @return tag
//     */
//    @GetMapping("/tags/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Tag getTag(@PathVariable long id) {
//        return service.getEntity(id);
//    }
//
//    /**
//     * update tag by id
//     * @param tag the tag
//     * @param id the id
//     * @return the exposed tag
//     */
//    @PatchMapping("/tags/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Tag updateTag(@RequestBody Tag tag, @PathVariable long id) {
//        service.updateEntity(id, tag);
//        return tag;
//    }
//
//    /**
//     * delete tag by id
//     * @param id the id
//     * @return string response
//     */
//    @DeleteMapping("/tags/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public String deleteTag(@PathVariable long id) {
//         service.deleteEntity(id);
//        return "Tag with ID = " + id + " was deleted.";
//    }
//}
