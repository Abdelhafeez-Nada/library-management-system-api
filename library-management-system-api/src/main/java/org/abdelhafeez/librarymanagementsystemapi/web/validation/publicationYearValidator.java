package org.abdelhafeez.librarymanagementsystemapi.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class publicationYearValidator implements ConstraintValidator<PublicationYear, Short> {

    @Override
    public boolean isValid(Short value, ConstraintValidatorContext context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isValid'");
    }

}
