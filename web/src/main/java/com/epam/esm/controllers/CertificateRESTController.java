package com.epam.esm.controllers;

import com.epam.esm.entity.Certificate;
import com.epam.esm.servises.impl.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.1")
public class CertificateRESTController {

    @Autowired
    private CertificateService service;

    @PostMapping("/certificates")
    public Certificate addCertificate(@RequestBody Certificate certificate){
        service.saveEntity(certificate);
        return certificate;
    }

    @GetMapping("/certificates")
    public List<Certificate> listAllCertificates() {
        return service.getAllEntity();
    }

    @GetMapping("/certificates/{id}")
    public Certificate getCertificate(@PathVariable long id){
        return service.getEntity(id);
    }

    @PutMapping("/certificates")
    public Certificate updateCertificate(@RequestBody Certificate certificate){
        service.saveEntity(certificate);
        return certificate;
    }

    @DeleteMapping("/certificates/{id}")
    public String deleteCertificate(@PathVariable long id){
        service.deleteEntity(id);
        return "Certificate with ID = " + id + " was deleted.";
    }
}
