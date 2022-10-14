package com.epam.esm.util.validator.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.repository.impl.CertificateRepositoryImpl;
import com.epam.esm.util.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("certificateValidator")
public class CertificateValidator implements Validator<Certificate> {

    @Autowired
    private CertificateRepositoryImpl certificateRepository;

    private static final int MIN_LETTERS = 2;
    private static final int MAX_LETTERS = 120;
    private static final String TEXT_PATTERN = "[^\s]+[A-Za-z0-9-\s'\"-.]*";//"[^\s]+[A-Za-zА-я0-9-\s'\"-.]*" посде добовления русского

    @Override
    public boolean isValid(Certificate certificate) {
        return (isNameValid(certificate.getCertificateName()) &&
                isPriceValid(certificate.getPrice()) &&
                isDurationValid(certificate.getDuration()) &&
                isDescriptionValid(certificate.getDescription()));
    }

    public boolean isNameValid(String name) {
        boolean result = false;
        if (name == null) {
        } else if (name.length() >= MIN_LETTERS && name.length() <= MAX_LETTERS
                && name.matches(TEXT_PATTERN)) {
            result = true;
        }
        return result;
    }

    public boolean isDescriptionValid(String description) {
        boolean result = false;
        if (description == null) {
        } else if (description.length() >= MIN_LETTERS && description.length() <= MAX_LETTERS) {
            result = true;
        }
        return result;
    }

    public boolean isDurationValid(int duration) {
        boolean result = false;
        if (duration >= 1) {
            result = true;
        }
        return result;
    }

    public boolean isPriceValid(double price) {
        boolean result = false;
        if (price >= 0) {
            result = true;
        }
        return result;
    }

}
