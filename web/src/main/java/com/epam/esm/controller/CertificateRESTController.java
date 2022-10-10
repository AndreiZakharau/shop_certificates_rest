package com.epam.esm.controller;

import com.epam.esm.entity.Certificate;
import com.epam.esm.mapper.impl.certificateMapper.CreateCertificateFromOnlyCertificateMapper;
import com.epam.esm.Dto.certificateDto.ReadCertificate;
import com.epam.esm.Dto.certificateDto.CreateCertificate;
import com.epam.esm.pagination.Pagination;
import com.epam.esm.service.impl.CertificateServiceImpl;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1.1")
public class CertificateRESTController {

    private final CertificateServiceImpl service;
    private final CreateCertificateFromOnlyCertificateMapper certificateMapper;

    /**
     * created certificateDto
     * enter the following fields: name,description,duration,price
     * dates computed automatically
     *
     * @param certificate the certificateDto
     * @return new certificateDto
     */
    @PostMapping("/certificates")
    @ResponseStatus(HttpStatus.CREATED)
    public Certificate addCertificate(@RequestBody Certificate certificate) {
        service.saveEntity(certificate);
        return certificate;
    }


    /**
     * @param page the page
     * @param size the size
     * @return list certificateDto
     */
    @GetMapping("/certificates")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ReadCertificate> listAllCertificates(@RequestParam("page") int page,
                                                                @RequestParam("size") int size) {
        int offset = Pagination.offset(page, size);
        int totalRecords = service.countAllCertificates();
        int pages = Pagination.findPages(totalRecords, size);
        int lastPage = Pagination.findLastPage(pages, size, totalRecords);
        Link prevLink = linkTo(methodOn(CertificateRESTController.class).listAllCertificates(Pagination.findPrevPage(page), size))
                .withRel("prev");
        Link nextLink = linkTo(methodOn(CertificateRESTController.class).listAllCertificates(Pagination.findNextPage(page, lastPage), size))
                .withRel("next");
        List<ReadCertificate> models = service.getAllEntity(size, offset);
        return CollectionModel.of(models, prevLink, nextLink);
    }

    /**
     * get certificateDto by id
     *
     * @param id the id
     * @return certificateDto
     */
    @GetMapping("/certificates/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ReadCertificate> getCertificateById(@PathVariable long id) {
        Optional<ReadCertificate> model = Optional.ofNullable(service.getEntity(id)).get();
        model.get().add(linkTo(methodOn(CertificateRESTController.class)
                .listAllCertificates(1,5))
                .withRel("certificateDto")
                .withType(HttpMethod.GET.name()));;
        return model;
    }

    /**
     * update certificateDto
     * enter the fields that you want to update
     * date not updated except the date
     *
     * @param certificate the certificateDto
     * @param id          the id
     * @return updated certificateDto
     */
    @PatchMapping("/certificates/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Certificate updateCertificate(@RequestBody CreateCertificate certificate, @PathVariable long id) {
        service.updateEntity(id, certificate);
        return certificateMapper.mapFrom(certificate);
    }

    /**
     * delete certificateDto by id
     *
     * @param id the id
     * @return string response
     */
    @DeleteMapping("/certificates/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteCertificate(@PathVariable long id) {
        service.deleteEntity(id);
        return "Certificate with ID = " + id + " was deleted.";
    }

    /**
     * @param name the name
     * @return certificateDto by name or part of name
     */
    @GetMapping("/certificates/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReadCertificate> getCertificateByName(@PathVariable String name) {
        return service.getCertificateByName(name);
    }

    /**
     * @param tagName the tagName
     * @return certificateDto by tagName
     */
    @GetMapping("/certificates/tag/{tagName}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReadCertificate> getCertificateByTag(@PathVariable String tagName) {
        return service.getCertificatesByTag(tagName);
    }

    @GetMapping("/certificates/tags")
    public List<ReadCertificate> getCertificatesByTags(@RequestParam String tagName1,
                                                       @RequestParam String tagName2) {
        return service.getCertificatesByTags(tagName1, tagName2);
    }

}
