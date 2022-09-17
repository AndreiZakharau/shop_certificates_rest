package com.epam.esm.servises.impl;

import com.epam.esm.entitys.Certificate;
import com.epam.esm.entitys.Tag;
import com.epam.esm.mapper.impl.certificateMapper.CreateCertificateFromOnlyCertificateMapper;
import com.epam.esm.mapper.impl.certificateMapper.ModelCertificateReadMapper;
import com.epam.esm.mapper.impl.certificateMapper.OnlyCertificateReadMapper;
import com.epam.esm.models.certificates.ModelCertificate;
import com.epam.esm.repositorys.impl.CertificateRepositoryImpl;
import com.epam.esm.repositorys.impl.TagRepositoryImpl;
import com.epam.esm.util.impl.CertificateValidator;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
class CertificateServiceImplTest {

    private CertificateServiceImpl certificateServiceImpl;
    private final CertificateValidator mockValidator = Mockito.mock(CertificateValidator.class);
    private final CertificateRepositoryImpl mockRepository = Mockito.mock(CertificateRepositoryImpl.class);
    private final TagRepositoryImpl mockTag = Mockito.mock(TagRepositoryImpl.class);
    private final ModelCertificateReadMapper mockCertificateReadMapper = Mockito.mock(ModelCertificateReadMapper.class);
    private final CreateCertificateFromOnlyCertificateMapper mockCertificateMapper = Mockito.mock(CreateCertificateFromOnlyCertificateMapper.class);
    private final OnlyCertificateReadMapper mockOnlyCertificateReadMapper =Mockito.mock(OnlyCertificateReadMapper.class);

    private static final Certificate CERTIFICATE_1 = Certificate.builder()
            .id(1L)
            .certificateName("coffee")
            .description("operates in a chain of cafes 'My Coffee'")
            .price(1.5)
            .duration(30)
            .createDate(LocalDateTime.now())
            .lastUpdateDate(LocalDateTime.now().plusDays(30))
            .build();
    private static final Certificate CERTIFICATE_2 = Certificate.builder()
            .id(7L)
            .certificateName("free tea")
            .description("operates in a chain of cafes 'My Coffee'")
            .price(0)
            .duration(10)
            .createDate(LocalDateTime.now())
            .lastUpdateDate(LocalDateTime.now().plusDays(10))
            .build();
    private static final List<Certificate> LIST = List.of(CERTIFICATE_1,CERTIFICATE_2);

    @BeforeEach
    void setUp() {
        certificateServiceImpl = new CertificateServiceImpl(mockRepository,mockValidator,mockTag,mockCertificateReadMapper,mockCertificateMapper);
    }

    @AfterEach
    void tearDown() {
        certificateServiceImpl = null;
    }

    @Test
    void getAllEntity() {

        int limit=1;
        int offset=2;
        Mockito.when(mockRepository.getAllEntity(limit,offset)).thenReturn(LIST);
        mockRepository.getAllEntity(limit,offset);
        Assertions.assertNotNull(LIST);
        Mockito.verify(mockRepository).getAllEntity(limit, offset);
        Assertions.assertEquals(LIST.size(),2);

    }

    @Test
    void saveEntity() {
        Certificate certificate = CERTIFICATE_1;
        Mockito.doNothing().when(mockRepository).addEntity(certificate);
        Mockito.when(mockValidator.isValid(certificate)).thenReturn(true);
        Assertions.assertEquals(certificate.getCertificateName(), "coffee");
        Assertions.assertEquals(certificate.getDuration(), 30);
    }

    @Test
    void updateEntity() {
        Certificate certificate = CERTIFICATE_2;
        Assertions.assertEquals(certificate.getCertificateName(), "free tea");
        String name = "sale 50%";
        certificate.setCertificateName(name);
        Mockito.doNothing().when(mockRepository).addEntity(certificate);
        certificateServiceImpl.updateEntity(7L,mockOnlyCertificateReadMapper.mapFrom(certificate));
        Assertions.assertEquals(certificate.getCertificateName(), name);
    }

    @Test
    void getEntity() {

        Mockito.when(mockRepository.getEntityById(1L)).thenReturn(Optional.of(CERTIFICATE_1));
        Optional<ModelCertificate> modelCertificate = mockRepository.getEntityById(1L).map(mockCertificateReadMapper::mapFrom);
        Assertions.assertNotNull(modelCertificate);
        Mockito.verify(mockRepository).getEntityById(Mockito.anyLong());
    }

    @Test
    void deleteEntity() {

        Mockito.when(mockRepository.getEntityById(2L)).thenReturn(Optional.of(CERTIFICATE_2));
        certificateServiceImpl.deleteEntity(2);
        Mockito.when(mockRepository.getEntityById(2)).thenReturn(null);

    }


    @Test
    void getCertificatesByName(){

        String name = "coffee";
        Mockito.when(mockRepository.getCertificatesByName(name)).thenReturn(List.of(CERTIFICATE_1));
        List<Certificate> certificates = mockRepository.getCertificatesByName(name);
        Assertions.assertNotNull(certificates);
        Assertions.assertEquals(certificates.size(), 1);
        Assertions.assertNotEquals(LIST,certificates);
    }

    @Test
    void getCertificatesByTag(){

        Tag tag = Tag.builder().id(6L).tagName("tea").build();
        Mockito.when(mockRepository.getCertificatesByName(tag.getTagName())).thenReturn(List.of(CERTIFICATE_2));
        List<Certificate> certificates = mockRepository.getCertificatesByName(tag.getTagName());
        Assertions.assertNotNull(certificates);
        Assertions.assertNotEquals(LIST,certificates);
        Assertions.assertEquals(certificates.size(),1);
    }

    @Test
    void countAllCertificates() {

       Mockito.when(mockRepository.countAllCertificates()).thenReturn(2);
       int count = mockRepository.countAllCertificates();
       Assertions.assertEquals(count,2);
    }

}