package com.epam.esm.util.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.util.Validator;
import org.springframework.stereotype.Service;

@Service("certificateValidator")
public class CertificateValidator implements Validator<Certificate> {

    private static final int MIN_LETTERS = 2;
    private static final int MAX_LETTERS = 120;
    private static final String TEXT_PATTERN = "[^\s]+[A-zА-я0-9-\\s'\"-.]*";

    @Override
    public boolean isValid(Certificate certificate) {
        return isNameValid(certificate.getCertificateName()) &&
                isDescriptionValid(certificate.getDescription()) &&
                isPriceValid(certificate.getPrice()) &&
                isDurationValid(certificate.getDuration());
    }

    public boolean isNameValid(String name) {
        if (name == null) {
            return false;
        } else
            return name.length() >= MIN_LETTERS && name.length() <= MAX_LETTERS
                    && name.matches(TEXT_PATTERN);
    }

    public boolean isDescriptionValid(String description) {
        if (description == null) {
            return false;
        } else
            return description.length() >= MIN_LETTERS && description.length() <= MAX_LETTERS
                    && description.matches(TEXT_PATTERN);
    }

    public boolean isDurationValid(int duration) {
        return duration >= 1;
    }

    public boolean isPriceValid(double price) {
        return price >= 0;
    }
}
