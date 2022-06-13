package com.epam.esm.servises.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repositorys.impl.TagRepositoryImpl;
import com.epam.esm.util.impl.TagsValidator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
class TagServiceImplTest {

    private TagServiceImpl tagServiceImpl;
    private final TagRepositoryImpl mockTagRepositoryImpl = Mockito.mock(TagRepositoryImpl.class);
    private final TagsValidator mockTagValid = Mockito.mock(TagsValidator.class);
    private final CertificateServiceImpl mockCertificateServiceImpl = Mockito.mock(CertificateServiceImpl.class);


    @BeforeEach
    public void setMoc(){
        tagServiceImpl = new TagServiceImpl(mockTagRepositoryImpl,mockTagValid, mockCertificateServiceImpl);
    }

    @AfterEach
    public void afterMetods(){
        tagServiceImpl = null;
    }

    @Test
    void getAllEntity() {
        Mockito.when(mockTagRepositoryImpl.getAllEntity()).thenReturn(List.of(new Tag(1L,"sale"),
                new Tag(2L,"free"),new Tag(3L,"10%")));
        List<Tag> list = tagServiceImpl.getAllEntity();
        Assertions.assertNotNull(list);
        Mockito.verify(mockTagRepositoryImpl).getAllEntity();
        Assertions.assertEquals(list.size(), 3);
    }

    @Test
    void saveEntity() {
        Tag tag = new Tag(13L,"ok");
        Mockito.doNothing().when(mockTagRepositoryImpl).addEntity(tag);
        Mockito.when(mockTagValid.isValid(tag)).thenReturn(true);
        Assertions.assertEquals(tag.getId(),13L);
        Assertions.assertEquals(tag.getTagName(),"ok");
    }

    @Test
    void updateEntity() {
        Tag tag = new Tag(13L,"ok");
        Assertions.assertEquals(tag.getTagName(), "ok");
        String name = "double";
        tag.setTagName(name);
        Mockito.doNothing().when(mockTagRepositoryImpl).addEntity(tag);
        tagServiceImpl.updateEntity(13L, tag);
        Assertions.assertEquals(tag.getTagName(), name);
    }

    @Test
    void getEntity() {
        Tag tag = new Tag(3L,"sale");
        Mockito.when(mockTagRepositoryImpl.getEntity(3)).thenReturn(java.util.Optional.of(tag));
        Optional<Tag> t = tagServiceImpl.getEntity(3);
        Assertions.assertNotNull(t);
        Mockito.verify(mockTagRepositoryImpl).getEntity(Mockito.anyLong());
    }

    @Test
    void deleteEntity() {
        Tag tag = new Tag(3L,"sale");
        Mockito.when(mockTagRepositoryImpl.getEntity(3)).thenReturn(Optional.of(tag));
        tagServiceImpl.deleteEntity(3);
        Mockito.verify(mockTagRepositoryImpl).deleteEntity(Mockito.anyLong());
        Mockito.when(mockTagRepositoryImpl.getEntity(3)).thenReturn(null);
    }
}