package org.abdelhafeez.librarymanagementsystemapi.web.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = publicationYearValidator.class)
@Documented
public @interface PublicationYear {

    String message() default "Invalid publication year!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
