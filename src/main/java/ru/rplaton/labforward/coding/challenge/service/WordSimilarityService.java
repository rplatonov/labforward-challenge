package ru.rplaton.labforward.coding.challenge.service;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * Service for comparing the words.
 */
@Validated
public interface WordSimilarityService {

    boolean isSimilar(@NotBlank final String word, @NotBlank final String candidate);
}
