//package com.epam.esm.controllers;
//
//import com.epam.esm.entitys.Certificate;
//import com.epam.esm.mapper.CertificateMapping;
//import com.epam.esm.models.certificates.OnlyCertificate;
//import com.epam.esm.models.certificates.ModelCertificateWithTags;
//import com.epam.esm.pagination.Pagination;
//import com.epam.esm.servises.impl.CertificateServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.hateoas.CollectionModel;
//import org.springframework.hateoas.Link;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/v1.1")
//public class CertificateRESTController {
//
//    private final CertificateServiceImpl service;
//    private final CertificateMapping map;
//
//    /**
//     * created certificate
//     * enter the following fields: name,description,duration,price
//     * dates computed automatically
//     * @param certificate the cer
//     *                    tificate
//     * @return new certificate
//     */
//    @PostMapping("/certificates")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Certificate addCertificate(@RequestBody Certificate certificate){
//        service.saveEntity(certificate);
//        return certificate;
//    }
//
//
//    /**
//     * @param page the page
//     * @param size the size
//     * @return list certificates
//     */
//    @GetMapping("/certificates")
//    @ResponseStatus(HttpStatus.OK)
//    public CollectionModel<OnlyCertificate> listAllCertificates(@RequestParam("page") int page,
//                                                                @RequestParam("size") int size) {
//        int offset = Pagination.offset(page, size);
//        int totalRecords = service.countAllCertificates();
//        int pages = Pagination.findPages(totalRecords, size);
//        int lastPage = Pagination.findLastPage(pages, size, totalRecords);
//        Link prevLink = linkTo(methodOn(CertificateRESTController.class).listAllCertificates(Pagination.findPrevPage(page), size))
//                .withRel("prev");
//        Link nextLink = linkTo(methodOn(CertificateRESTController.class).listAllCertificates(Pagination.findNextPage(page, lastPage), size))
//                .withRel("next");
//        List<OnlyCertificate> models = service.getAllEntity(size, offset);
//        return CollectionModel.of(models, prevLink, nextLink);
//    }
//
//    /**
//     * get certificate by id
//     * @param id the id
//     * @return certificate
//     */
//    @GetMapping("/certificates/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Optional<Certificate> getCertificateById(@PathVariable long id){return service.getEntity(id);
//    }
//
//    /**
//     * update certificate
//     * enter the fields that you want to update
//     * date not updated except the date
//     * @param certificate the certificate
//     * @param id the id
//     * @return updated certificate
//     */
//    @PatchMapping ("/certificates/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Certificate updateCertificate(@RequestBody OnlyCertificate certificate, @PathVariable long id){
//        service.updateEntity(id,certificate);
//        return map.buildCertificate(certificate);
//    }
//
//    /**
//     * delete certificate by id
//     * @param id the id
//     * @return string response
//     */
//    @DeleteMapping("/certificates/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public String deleteCertificate(@PathVariable long id){
//        service.deleteEntity(id);
//        return "Certificate with ID = " + id + " was deleted.";
//    }
//
//    /**
//     *
//     * @param name the name
//     * @return certificates by name or part of name
//     */
//    @GetMapping("/certificates/name/{name}")
//    @ResponseStatus(HttpStatus.OK)
//    public List<Certificate> getCertificateByName(@PathVariable String name){
//        return service.getCertificateByName(name);
//    }
//
//    /**
//     * @return certificates by tag
//     */
//    @GetMapping("/certificates/tags")
//    @ResponseStatus(HttpStatus.OK)
//    public CollectionModel<ModelCertificateWithTags> getAllCertificatesAndTags(@RequestParam("page") int page,
//                                                                               @RequestParam("size") int size) {
//        int offset = Pagination.offset(page, size);
//        int totalRecords = service.countAllCertificates();
//        int pages = Pagination.findPages(totalRecords, size);
//        int lastPage = Pagination.findLastPage(pages, size, totalRecords);
//        Link prevLink = linkTo(methodOn(CertificateRESTController.class).listAllCertificates(Pagination.findPrevPage(page), size))
//                .withRel("prev");
//        Link nextLink = linkTo(methodOn(CertificateRESTController.class).listAllCertificates(Pagination.findNextPage(page, lastPage), size))
//                .withRel("next");
//        List<ModelCertificateWithTags> certificates =  service.getAllCertificatesAndTags(size, offset);
//        return CollectionModel.of(certificates, prevLink, nextLink);
//    }
//
//    @GetMapping("/all")
//    public CollectionModel<Certificate> getAllCertificates(@RequestParam("page") int page,
//                                                                  @RequestParam("size") int size) {
//        int offset = Pagination.offset(page, size);
//        int totalRecords = service.countAllCertificates();
//        int pages = Pagination.findPages(totalRecords, size);
//        int lastPage = Pagination.findLastPage(pages, size, totalRecords);
//        Link prevLink = linkTo(methodOn(CertificateRESTController.class).listAllCertificates(Pagination.findPrevPage(page), size))
//                .withRel("prev");
//        Link nextLink = linkTo(methodOn(CertificateRESTController.class).listAllCertificates(Pagination.findNextPage(page, lastPage), size))
//                .withRel("next");
//        List<Certificate> certificates = service.getAllCertificates(size, offset);
//        return CollectionModel.of(certificates, prevLink, nextLink);
//    }
//
////    /**
////     * Sort the list of certificates by date, by name.
////     * to create, enter the asc field for
////     *  - dates, in the -  box for the name
////     *  enter "ASC" or "DESC"
////     * @param asc string "ASC"
////     * @param desc string "DESC"
////     * @return certificates
////     */
////    @GetMapping("/certificates/sorted")
////    @ResponseStatus(HttpStatus.OK)
////    public List<Certificate> getSortedCertificates(@RequestParam String asc, @RequestParam String desc) {
////        return service.getSortedCertificates(asc,desc);
////    }
//
//}
