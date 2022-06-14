package com.epam.esm.controllers;

import com.epam.esm.entity.Certificate;
import com.epam.esm.servises.impl.CertificateServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1.1")
public class CertificateRESTController {

    private final CertificateServiceImpl service;

    public CertificateRESTController(@Qualifier("certificateService") CertificateServiceImpl service) {
        this.service = service;
    }

    /**
     * created certificate
     * enter the following fields: name,description,duration,price
     * dates computed automatically
     * @param certificate the certificate
     * @return new certificate
     */
    @PostMapping("/certificates")
    @ResponseStatus(HttpStatus.CREATED)
    public Certificate addCertificate(@RequestBody Certificate certificate){
        service.saveEntity(certificate);
        return certificate;
    }

    /**
     * @return list certificates
     */
    @GetMapping("/certificates")
    @ResponseStatus(HttpStatus.OK)
    public Page<Certificate> listAllCertificates(@PageableDefault(sort = {"id"},direction = Sort.Direction.DESC)Pageable pageable) {
        return service.getAllEntity(pageable);
    }

    /**
     * get certificate by id
     * @param id the id
     * @return certificate
     */
    @GetMapping("/certificates/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Certificate> getCertificateById(@PathVariable long id){return service.getEntity(id);
    }

    /**
     * update certificate
     * enter the fields that you want to update
     * date not updated except the date
     * @param certificate the certificate
     * @param id the id
     * @return updated certificate
     */
    @PatchMapping ("/certificates/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Certificate updateCertificate( @RequestBody Certificate certificate, @PathVariable long id){
        service.updateEntity(id,certificate);
        return certificate;
    }

    /**
     * delete certificate by id
     * @param id the id
     * @return string response
     */
    @DeleteMapping("/certificates/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteCertificate(@PathVariable long id){
        service.deleteEntity(id);
        return "Certificate with ID = " + id + " was deleted.";
    }

    /**
     *
     * @param name the name
     * @return certificates by name or part of name
     */
    @GetMapping("/certificates/tag/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<Certificate> getCertificateByName(@PathVariable String name){
        return service.getCertificateByName(name);
    }

    /**
     * @return certificates by tag
     */
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Page<Object[]> getAllCertificatesAndTags(@PageableDefault(sort = {"id"},direction = Sort.Direction.DESC)Pageable pageable){
        return service.getAllCertificatesAndTags(pageable);
    }
//
//    /**
//     * Sort the list of certificates by date, by name.
//     * to create, enter the asc field for
//     *  - dates, in the -  box for the name
//     *  enter "ASC" or "DESC"
//     * @param asc string "ASC"
//     * @param desc string "DESC"
//     * @return certificates
//     */
//    @GetMapping("/certificates/sorted")
//    @ResponseStatus(HttpStatus.OK)
//    public List<Certificate> getSortedCertificates(@RequestParam String asc, @RequestParam String desc) {
//        return service.getSortedCertificates(asc,desc);
//    }

}
