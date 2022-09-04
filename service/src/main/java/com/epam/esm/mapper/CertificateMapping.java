//package com.epam.esm.mapper;
//
//import com.epam.esm.entitys.Certificate;
//import com.epam.esm.models.certificates.OnlyCertificate;
//import com.epam.esm.models.certificates.ModelCertificateWithTags;
//import com.epam.esm.models.tags.TagModel;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class CertificateMapping {
//
//    private final TagMapping tagMapping;
//
//    public CertificateMapping(TagMapping tagMapping) {
//        this.tagMapping = tagMapping;
//    }
//
//    public Certificate buildCertificate(OnlyCertificate cm) {
//        Certificate c = new Certificate();
//        c.setId(cm.getId());
//        c.setCertificateName(cm.getCertificateName());
//        c.setDescription(cm.getDescription());
//        c.setDuration(cm.getDuration());
//        c.setPrice(cm.getPrice());
//        c.setCreateDate(cm.getCreateDate());
//        c.setLastUpdateDate(cm.getLastUpdateDate());
//        return c;
//    }
//
//    public List<OnlyCertificate> buildListCertificates(List<Certificate> list) {
//        List<OnlyCertificate> certificates = new ArrayList<>();
//        for (Certificate c : list) {
//            OnlyCertificate cm = new OnlyCertificate();
//            cm.setId(c.getId());
//            cm.setCertificateName(c.getCertificateName());
//            cm.setDescription(c.getDescription());
//            cm.setDuration(c.getDuration());
//            cm.setPrice(c.getPrice());
//            cm.setCreateDate(c.getCreateDate());
//            cm.setLastUpdateDate(c.getLastUpdateDate());
//            certificates.add(cm);
//        }
//        return certificates;
//    }
//
//    public List<ModelCertificateWithTags> buildListCertificatesAndTags(List<Certificate> list) {
//        List<ModelCertificateWithTags> certificates = new ArrayList<>();
//        for (Certificate c : list) {
//            ModelCertificateWithTags cmt = new ModelCertificateWithTags();
//            cmt.setId(c.getId());
//            cmt.setCertificateName(c.getCertificateName());
//            cmt.setDescription(c.getDescription());
//            cmt.setDuration(c.getDuration());
//            cmt.setPrice(c.getPrice());
//            cmt.setCreateDate(c.getCreateDate());
//            cmt.setLastUpdateDate(c.getLastUpdateDate());
//            List<TagModel> tags = c.getTags().stream()
//                    .map(tagMapping::buildTagModel)
//                    .collect(Collectors.toList());
//            cmt.setTags(tags);
//            certificates.add(cmt);
//
//        }
//        return certificates;
//    }
//}
