package ru.rplaton.labforward.coding.challenge.service;

import org.springframework.validation.annotation.Validated;
import ru.rplaton.labforward.coding.challenge.dto.AnalyticsResult;
import ru.rplaton.labforward.coding.challenge.validator.WordToAnalyze;

import javax.validation.constraints.NotBlank;

@Validated
public interface TextAnalyticsService {

    AnalyticsResult makeAnalyzeByWord(@WordToAnalyze final String word, @NotBlank(message = "Text should not be blank.") final String text);

}
