package com.epam.esm.servises.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
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
    private final TagRepositoryImpl mockTag =Mockito.mock(TagRepositoryImpl.class);


    @BeforeEach
    void setUp() {
        certificateServiceImpl = new CertificateServiceImpl(mockRepository,mockValidator,mockTag);
    }

    @AfterEach
    void tearDown() {
        certificateServiceImpl = null;
    }

    @Test
    void getAllEntity() {
        Certificate certificate1 = new Certificate(1L,"free coffee","operates in a chain of cafes 'My Coffee'",
                0.0, 10, LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        Certificate certificate2 = new Certificate(2L,"20% sale", "operates in a chain of 'Good Car washing' ",
                10.0, 30, LocalDateTime.now(), LocalDateTime.now().plusDays(30));
        Mockito.when(mockRepository.getAllEntity()).thenReturn(List.of(certificate1, certificate2));
        List<Certificate> list = certificateServiceImpl.getAllEntity();
        Assertions.assertNotNull(list);
        Assertions.assertEquals(list.size(),2);
//        Mockito.verify(mockRepository.getAllEntity());
    }

    @Test
    void saveEntity() {
        Certificate certificate = new Certificate(11L,"free coffee","operates in a chain of cafes 'My Coffee'",
                0.0, 10, LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        Mockito.doNothing().when(mockRepository).addEntity(certificate);
        Mockito.when(mockValidator.isValid(certificate)).thenReturn(true);
        Assertions.assertEquals(certificate.getCertificateName(), "free coffee");
        Assertions.assertEquals(certificate.getDuration(), 10);
    }

    @Test
    void updateEntity() {
        Certificate certificate = new Certificate(11L,"free coffee","operates in a chain of cafes 'My Coffee'",
                0.0, 10, LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        Assertions.assertEquals(certificate.getCertificateName(), "free coffee");
        String name = "sale 50%";
        certificate.setCertificateName(name);
        Mockito.doNothing().when(mockRepository).addEntity(certificate);
        certificateServiceImpl.updateEntity(11L,certificate);
        Assertions.assertEquals(certificate.getCertificateName(), name);
    }

    @Test
    void getEntity() {
        Certificate certificate = new Certificate(11L,"free coffee","operates in a chain of cafes 'My Coffee'",
                0.0, 10, LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        Mockito.when(mockRepository.getEntity(11L)).thenReturn(Optional.of(certificate));
        Optional<Certificate> c = certificateServiceImpl.getEntity(11L);
        Assertions.assertNotNull(c);
//        Mockito.verify(mockRepository).getEntity(Mockito.anyLong());
    }

    @Test
    void deleteEntity() {

        Certificate certificate2 = new Certificate(2L,"20% sale", "operates in a chain of 'Good Car washing' ",
                10.0, 30, LocalDateTime.now(), LocalDateTime.now().plusDays(30));

        Mockito.when(mockRepository.getEntity(2L)).thenReturn(Optional.of(certificate2));
        certificateServiceImpl.deleteEntity(2);
        Mockito.when(mockRepository.getEntity(2)).thenReturn(null);

    }


    @Test
    void getCertificatesByName(){
        Certificate certificate1 = new Certificate(1L,"free coffee","operates in a chain of cafes 'My Coffee'",
                0.0, 10, LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        Certificate certificate2 = new Certificate(2L,"20% sale", "operates in a chain of 'Good Car washing' ",
                10.0, 30, LocalDateTime.now(), LocalDateTime.now().plusDays(30));
        String name = "coffee";
        Mockito.when(mockRepository.getCertificatesByName(name)).thenReturn(List.of(certificate1));
        List<Certificate> list = mockRepository.getCertificatesByName(name);
        Assertions.assertNotNull(list);
        Assertions.assertEquals(list.size(), 1);
    }

    @Test
    void getCertificatesByTag(){
        Certificate certificate1 = new Certificate(1L,"free coffee","operates in a chain of cafes 'My Coffee'",
                0.0, 10, LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        Certificate certificate2 = new Certificate(2L,"20% sale", "operates in a chain of 'Good Car washing' ",
                10.0, 30, LocalDateTime.now(), LocalDateTime.now().plusDays(30));
        Tag tag = new Tag(6L,"free");
        Mockito.when(mockRepository.getCertificatesByTag(tag)).thenReturn(List.of(certificate1));
        List<Certificate> list = mockRepository.getCertificatesByTag(tag);
        Assertions.assertNotNull(list);
        Assertions.assertEquals(list.size(),1);
    }
}