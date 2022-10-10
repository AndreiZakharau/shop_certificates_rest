package com.epam.esm.controller;

import com.epam.esm.entity.Certificate;
import com.epam.esm.mapper.impl.certificateMapper.CreateCertificateFromOnlyCertificateMapper;
import com.epam.esm.model.certificate.ModelCertificate;
import com.epam.esm.model.certificate.OnlyCertificate;
import com.epam.esm.pagination.Pagination;
import com.epam.esm.servise.impl.CertificateServiceImpl;
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
     * created certificate
     * enter the following fields: name,description,duration,price
     * dates computed automatically
     *
     * @param certificate the certificate
     * @return new certificate
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
     * @return list certificate
     */
    @GetMapping("/certificates")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ModelCertificate> listAllCertificates(@RequestParam("page") int page,
                                                                 @RequestParam("size") int size) {
        int offset = Pagination.offset(page, size);
        int totalRecords = service.countAllCertificates();
        int pages = Pagination.findPages(totalRecords, size);
        int lastPage = Pagination.findLastPage(pages, size, totalRecords);
        Link prevLink = linkTo(methodOn(CertificateRESTController.class).listAllCertificates(Pagination.findPrevPage(page), size))
                .withRel("prev");
        Link nextLink = linkTo(methodOn(CertificateRESTController.class).listAllCertificates(Pagination.findNextPage(page, lastPage), size))
                .withRel("next");
        List<ModelCertificate> models = service.getAllEntity(size, offset);
        return CollectionModel.of(models, prevLink, nextLink);
    }

    /**
     * get certificate by id
     *
     * @param id the id
     * @return certificate
     */
    @GetMapping("/certificates/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ModelCertificate> getCertificateById(@PathVariable long id) {
        Optional<ModelCertificate> model = Optional.ofNullable(service.getEntity(id)).get();
        model.get().add(linkTo(methodOn(CertificateRESTController.class)
                .listAllCertificates(1,5))
                .withRel("certificate")
                .withType(HttpMethod.GET.name()));;
        return model;
    }

    /**
     * update certificate
     * enter the fields that you want to update
     * date not updated except the date
     *
     * @param certificate the certificate
     * @param id          the id
     * @return updated certificate
     */
    @PatchMapping("/certificates/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Certificate updateCertificate(@RequestBody OnlyCertificate certificate, @PathVariable long id) {
        service.updateEntity(id, certificate);
        return certificateMapper.mapFrom(certificate);
    }

    /**
     * delete certificate by id
     *
     * @param id the id
     * @return string response
     */
    @DeleteMapping("/certificates/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteCertificate(@PathVariable long id) {
        service.deleteEntity(id);
        return "Certificate with ID = " + id + " was deleted.";
    }

    /**
     * @param name the name
     * @return certificate by name or part of name
     */
    @GetMapping("/certificates/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<ModelCertificate> getCertificateByName(@PathVariable String name) {
        return service.getCertificateByName(name);
    }

    /**
     * @param tagName the tagName
     * @return certificate by tagName
     */
    @GetMapping("/certificates/tag/{tagName}")
    @ResponseStatus(HttpStatus.OK)
    public List<ModelCertificate> getCertificateByTag(@PathVariable String tagName) {
        return service.getCertificatesByTag(tagName);
    }

    @GetMapping("/certificates/tags")
    public List<ModelCertificate> getCertificatesByTags(@RequestParam String tagName1,
                                                        @RequestParam String tagName2) {
        return service.getCertificatesByTags(tagName1, tagName2);
    }

}
