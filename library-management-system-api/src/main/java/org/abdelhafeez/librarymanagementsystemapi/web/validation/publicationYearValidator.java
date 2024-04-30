package org.abdelhafeez.librarymanagementsystemapi.web.validation;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class publicationYearValidator implements ConstraintValidator<PublicationYear, Short> {

    @Override
    public boolean isValid(Short value, ConstraintValidatorContext context) {
        if (value == null)
            return false;
        Short start = Short.valueOf(String.valueOf(1900));
        Short end = Short.valueOf(String.valueOf(LocalDate.now().getYear()));
        return value >= start && value <= end;
    }
}
