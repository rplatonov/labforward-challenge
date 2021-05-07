package ru.rplaton.labforward.coding.challenge.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = WordToAnalyzeValidator.class)
@Documented
public @interface WordToAnalyze {

    String message() default "Only one word is supported for analyze and it should not be blank.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
