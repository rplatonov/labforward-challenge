package ru.rplaton.labforward.coding.challenge.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WordToAnalyzeValidator implements ConstraintValidator<WordToAnalyze, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return false;
        }
        final String[] splitString = value.split("(?U)\\W+");
        return splitString.length == 1;
    }
}
