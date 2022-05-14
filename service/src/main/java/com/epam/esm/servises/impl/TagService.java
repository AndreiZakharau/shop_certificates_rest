//package com.epam.esm.servises.impl;
//
//import com.epam.esm.entity.Tag;
//import com.epam.esm.repositorys.impl.TagDao;
//import com.epam.esm.servises.EntityService;
//import com.epam.esm.util.impl.TagsValidator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service("tagService")
//public class TagService implements EntityService<Tag> {
//
//    private final TagDao tagDao;
//    //    private final CertificateService certificateService;
//
//    @Autowired
//    public TagService(TagDao tagDao, TagsValidator tagsValidator) {  //(TagDao tagDao, TagsValidator tagsValidator, CertificateService certificateService)
//        this.tagDao = tagDao;
//        //        this.certificateService = certificateService;
//
//    }
//
//    @Override
//    @Transactional
//    public List<Tag> getAllEntity() {
//        return tagDao.getAllEntity();
//    }
//
//    @Override
//    @Transactional
//    public void saveEntity(Tag tag) {
////        if(tagsValidator.isValid(tag)) {
//            tagDao.addEntity(tag);
////        }else {
////            throw new IncorrectDataException("message.not.valid");
////        }
////        certificateService.saveTagCertificates();
//    }
//
//    @Override
//    @Transactional
//    public void updateEntity(long id, Tag tag){
////        if(tagsValidator.isValid(tag)) {
//            tagDao.updateEntity(id, tag);
////        }else {
////            throw new IncorrectDataException("message.not.valid");
////        }
////        certificateService.saveTagCertificates();
//    }
//
//    @Override
//    @Transactional
//    public Tag getEntity(long id) {
//        Tag tag = tagDao.getEntity(id);
////        if(tag==null){
////            throw new NoSuchEntityException("message.tag.with.id");
////        }
//        return tag;
//    }
//
//    @Override
//    @Transactional
//    public void deleteEntity(long id) {
////        if(tagDao.getEntity(id)==null){
////            throw new NoSuchEntityException("message.tag.with.id");
////        }
//        tagDao.deleteEntity(id);
//    }
//}
