//package com.epam.esm.servises.impl;
//
//import com.epam.esm.entitys.Certificate;
//import com.epam.esm.entitys.Tag;
//import com.epam.esm.mapper.CertificateMapping;
//import com.epam.esm.models.certificates.OnlyCertificate;
//import com.epam.esm.models.certificates.ModelCertificateWithTags;
//import com.epam.esm.repositorys.impl.CertificateRepositoryImpl;
//import com.epam.esm.repositorys.impl.TagRepositoryImpl;
//import com.epam.esm.servises.CertificateService;
//import com.epam.esm.util.impl.CertificateValidator;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@Service("certificateService")
//@RequiredArgsConstructor
//public class CertificateServiceImpl implements CertificateService {
//
//    private final CertificateRepositoryImpl repository;
//    private final CertificateValidator certificateValidator;
//    private final TagRepositoryImpl tagRepository;
////    private final TagMapping tagMap;
//    private final CertificateMapping map;
//
//    @Override
//    @Transactional
//    public List<OnlyCertificate> getAllEntity(int limit, int offset) {
//        List<Certificate> list = repository.getAllEntity(limit, offset);
//        return map.buildListCertificates(list);
//    }
//
//    @Override
//    @Transactional
//    public void saveEntity(Certificate certificate) {
//        if (certificateValidator.isValid(certificate)){
//            if(certificate.getCreateDate() == null){
//                certificate.setCreateDate(LocalDateTime.now());
//            }
//            if(certificate.getLastUpdateDate() == null){
//                certificate.setLastUpdateDate(certificate.getCreateDate().plusDays(certificate.getDuration()));
//            }
//            repository.addEntity(certificate);
//            saveCertificatesTag();
//        }else{
//            System.out.println("NOT VALID!!!");//TODO бросаем исключение, что не валидно
//        }
//    }
//    @Override
//    @Transactional
//    public void updateEntity(long id, OnlyCertificate model) {
//        Optional <Certificate> c = repository.getEntity(id);
//        if (c.isPresent()) {
//            model.setId(id);
//            if(model.getCertificateName() == null)
//                model.setCertificateName(c.get().getCertificateName());
//            if(model.getDescription()== null)
//                model.setDescription(c.get().getDescription());
//            if(model.getPrice() <= 0)
//                model.setPrice(c.get().getPrice());
//            if(model.getDuration()<=0) {
//                model.setDuration(c.get().getDuration());
//                model.setLastUpdateDate(LocalDateTime.now().plusDays(c.get().getDuration()));
//            }else{
//                model.setLastUpdateDate(LocalDateTime.now().plusDays(model.getDuration()));
//            }
//            if(model.getCreateDate()==null)
//                model.setCreateDate(c.get().getCreateDate());
//            if(model.getLastUpdateDate()==null)
//                model.setLastUpdateDate(c.get().getLastUpdateDate());
//            Certificate certificate = map.buildCertificate(model);
//
//            if (certificateValidator.isValid(certificate)) {
//                repository.updateEntity(certificate);
//            }else{
//                //TODO бросаем исключение, что не валидно
//            }
//        }else{
//           // TODO (бросить исключение, что токого id нет)
//        }
//    }
//
//    @Override
//    @Transactional
//    public Optional<Certificate> getEntity(long id) {
//        Optional<Certificate> c = repository.getEntity(id);
//        if(c.isEmpty()) {
//            // TODO (бросить исключение, что токого id нет)
//        }
//            return c;
//    }
//
//    @Override
//    @Transactional
//    public void deleteEntity(long id) {
//        Optional<Certificate> c = repository.getEntity(id);
//        if (c.isPresent()) {
//            repository.deleteEntity(c.get());
//        }else {
//            //TODO (бросить исключение, что токого id нет)
//        }
//    }
//
//    @Override
//    @Transactional
//    public int countAllCertificates() {
//        return repository.countAllCertificates();
//    }
//
//    @Transactional
//    public List<Certificate> getAllCertificates(int limit, int offset){
//        return repository.getAllCertificates(limit,offset);
//    }
//
//    @Transactional
//    public void saveCertificatesTag(){
//
//        List<Tag> tagList = tagRepository.getOnlyTags();
//        for (Tag t : tagList) {
//            List<Certificate> certificateList = repository.getCertificatesByName(t.getTagName());
//            if (!certificateList.isEmpty()) {
//                for (Certificate c : certificateList) {
//                    if(repository.getCertificateAndTag(c,t).isEmpty()) {
//                        repository.saveCertificatesTag(c.getId(), t.getId());
//                    }
//                }
//            }
//        }
//    }
//
////    @Transactional
////    public List<Certificate> getCertificatesByTag(Tag t){
////        List<Certificate> list = repository.getCertificatesByTag(t.getTagName());
////        List<Certificate> result = null;
////        if(list.isEmpty()){
////            //TODO С таким тегом сертиыикаты не найдены
////        }else
////           result = repository.getCertificatesByTag(t.getTagName());
////
////        return result;
////    }
//
//    @Transactional
//    public List<Certificate> getCertificateByName(String name){
//        List<Certificate> list = repository.getCertificatesByName(name);
//        if(list.isEmpty()) {
//            //TODO С таким именем сертиыикаты не найдены
//        }
//            return list;
//
//    }
//    @Transactional
//    public List<ModelCertificateWithTags> getAllCertificatesAndTags(int limit, int offset) {
//        List<Certificate> list = repository.getAllCertificatesAndTags(limit, offset);
//        return map.buildListCertificatesAndTags(list);
//    }
//
//}
