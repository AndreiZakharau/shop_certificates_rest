package com.epam.esm.controller;

import com.epam.esm.Dto.certificateDto.CertificateDto;
import com.epam.esm.Dto.certificateDto.CreateCertificate;
import com.epam.esm.Dto.certificateDto.ReadCertificate;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateFromCreateCertificate;
import com.epam.esm.pagination.Pagination;
import com.epam.esm.service.impl.CertificateServiceImpl;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1.1/certificates")
public class CertificateController {  //TODO remake

    private final CertificateServiceImpl service;
    private final TransitionCertificateFromCreateCertificate certificateMapper;

    /**
     * created certificate
     * enter the following fields: name,description,duration,price
     * dates computed automatically
     *
     * @param certificate the CreateCertificate (certificate Dto)
     * @return new createCertificate (certificate Dto)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCertificate addCertificate(@RequestBody CreateCertificate certificate) {
        service.saveEntity(certificate);
        return certificate;
    }


    /**
     * @param page the page
     * @param size the size
     * @return list ReadCertificate (certificate Dto)
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ReadCertificate> listAllCertificates(@RequestParam(value = "page", required = false) Integer page,
                                                                @RequestParam(value = "size", required = false) Integer size) {
        int offset = Pagination.offset(page, size);
        int totalRecords = service.countAll();
        int pages = Pagination.findPages(totalRecords, size);
        int lastPage = Pagination.findLastPage(pages, size, totalRecords);
        Link prevLink = linkTo(methodOn(CertificateController.class).listAllCertificates(Pagination.findPrevPage(page), size))
                .withRel("prev");
        Link nextLink = linkTo(methodOn(CertificateController.class).listAllCertificates(Pagination.findNextPage(page, lastPage), size))
                .withRel("next");
        List<ReadCertificate> models = service.getAllEntity(size, offset);
        return CollectionModel.of(models, prevLink, nextLink);
    }

    /**
     * get certificateDto by id
     *
     * @param id the id
     * @return ReadCertificate (certificate Dto)
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ReadCertificate> getCertificateById(@PathVariable("id") Long id) {
        Optional<ReadCertificate> model = Optional.ofNullable(service.findById(id)).get();
        model.get().add(linkTo(methodOn(CertificateController.class)
                .listAllCertificates(1, 5))
                .withRel("certificateDto")
                .withType(HttpMethod.GET.name()));
        ;
        return model;
    }

    /**
     * update certificate
     * enter the fields that you want to update
     * date not updated except the date
     *
     * @param certificate the ReadCertificate (certificate Dto)
     * @param id          the id
     * @return updated ReadCertificate (certificate Dto)
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto updateCertificate(@RequestBody CertificateDto certificate, @PathVariable long id) {
        service.updateEntity(id, certificate);
        return certificate;
    }

    /**
     * delete certificate by id
     *
     * @param id the id
     * @return string response
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteCertificate(@PathVariable long id) {
        service.deleteEntity(id);
        return "Certificate with ID = " + id + " was deleted.";
    }


    @GetMapping("/sort")
    @ResponseStatus(HttpStatus.OK)
    public List<ReadCertificate> getCertificateByParameters(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "tagName", required = false) List<String> tagNames,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price", required = false) List<Double> price,
            @RequestParam(value = "page", defaultValue ="1", required = false) Integer page,
            @RequestParam(value = "size", defaultValue ="10", required = false) Integer size
    ) {

        return service.getCertificateByParameters(name, tagNames,description,price,page,size);
    }

    /**
     * @param tagNames the list tagName
     * @return list ReadCertificate (certificate Dto)
     */
    @GetMapping("/names")
    @ResponseStatus(HttpStatus.OK)
    public List<ReadCertificate> getCertificateByTag(@RequestParam(value = "tagName", required = false) List<String> tagNames) {
        return service.getCertificatesByTags(tagNames);
    }


}
