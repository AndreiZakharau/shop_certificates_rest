package com.epam.esm.servises.impl;


import com.epam.esm.entitys.Tag;
import com.epam.esm.mapper.impl.tagMapper.OnlyTagReadMapper;
import com.epam.esm.mapper.impl.tagMapper.TagModelReadMapper;
import com.epam.esm.models.tags.TagModel;
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
    private final TagModelReadMapper mockTagModel = Mockito.mock(TagModelReadMapper.class);
    private final OnlyTagReadMapper mockOnlyTag = Mockito.mock(OnlyTagReadMapper.class);

    private final static Tag TAG = Tag.builder()
            .id(13L)
            .tagName("coffee")
            .build();
    private final static Tag TAG2 = Tag.builder()
            .id(11L)
            .tagName("sale")
            .build();


    @BeforeEach
    public void setMoc(){
        tagServiceImpl = new TagServiceImpl(mockTagRepositoryImpl,mockTagValid, mockCertificateServiceImpl,mockTagModel,mockOnlyTag);
    }

    @AfterEach
    public void afterMetods(){
        tagServiceImpl = null;
    }

    @Test
    void getAllEntity() {
        int limit=1;
        int offset=2;
        Mockito.when(mockTagRepositoryImpl.getAllEntity(limit, offset)).thenReturn( List.of(TAG,TAG2));
        List<Tag> list = mockTagRepositoryImpl.getAllEntity(limit, offset);
        Assertions.assertNotNull(list);
        Mockito.verify(mockTagRepositoryImpl).getAllEntity(limit, offset);
        Assertions.assertEquals(list.size(),2);
    }

    @Test
    void saveEntity() {
        Tag tag = TAG2;
        Mockito.doNothing().when(mockTagRepositoryImpl).addEntity(tag);
        Mockito.when(mockTagValid.isValid(tag)).thenReturn(true);
        Assertions.assertEquals(tag.getId(),11L);
        Assertions.assertEquals(tag.getTagName(),"sale");
    }

    @Test
    void updateEntity() {
        Tag tag = TAG;
        Assertions.assertEquals(tag.getTagName(), "coffee");
        String name = "free";
        tag.setTagName(name);
        Mockito.doNothing().when(mockTagRepositoryImpl).addEntity(tag);
        tagServiceImpl.updateEntity(13L, mockTagModel.mapFrom(tag));
        Assertions.assertEquals((tag.getTagName()),name );
    }

    @Test
    void findById() {
        Mockito.when(mockTagRepositoryImpl.getEntityById(13)).thenReturn(java.util.Optional.of(TAG));
        Optional<TagModel> t = tagServiceImpl.findById(13);
        Assertions.assertNotNull(t);
        Mockito.verify(mockTagRepositoryImpl).getEntityById(Mockito.anyLong());
    }

    @Test
    void deleteEntity() {
        Mockito.when(mockTagRepositoryImpl.getEntityById(13L)).thenReturn(Optional.of(TAG));
        tagServiceImpl.deleteEntity(13);
        Mockito.verify(mockTagRepositoryImpl).deleteEntity(Mockito.anyLong());
        Mockito.when(mockTagRepositoryImpl.getEntityById(13)).thenReturn(null);
    }

    @Test
    void listOnlyTags() {
        List<Tag> tags = List.of(TAG,TAG2);
        Mockito.when(mockTagRepositoryImpl.getOnlyTags()).thenReturn(tags);
        List<Tag> list = mockTagRepositoryImpl.getOnlyTags();
        Mockito.verify(mockTagRepositoryImpl).getOnlyTags();
        Assertions.assertNotNull(list);
        Assertions.assertEquals(list.size(),2);

    }



    @Test
    void countAllTags() {
        List<Tag> tags = List.of(TAG,TAG2);
        Mockito.when(mockTagRepositoryImpl.countAllTags()).thenReturn(tags.size());
        int counts = tagServiceImpl.countAllTags();
        Mockito.verify(mockTagRepositoryImpl).countAllTags();
        Assertions.assertEquals(counts,2);
    }

    @Test
    void getPopularTagWithUser() {
    }
}