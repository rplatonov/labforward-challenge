package ru.rplaton.labforward.coding.challenge.service.impl;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.rplaton.labforward.coding.challenge.service.WordSimilarityService;

/**
 * Word comparing service based on Levenshtein algorithm.
 */
@Service
public class LevenshteinWordSimilarityService implements WordSimilarityService {

    private final LevenshteinDistance levenshteinDistance;

    private final Integer requiredDistance;

    public LevenshteinWordSimilarityService(LevenshteinDistance levenshteinDistance,
                                            @Value("${text.analyzer.required.distance}") Integer requiredDistance) {
        this.levenshteinDistance = levenshteinDistance;
        this.requiredDistance = requiredDistance;
    }

    @Override
    public boolean isSimilar(final String word, final String candidate) {
        final Integer distance = levenshteinDistance.apply(word, candidate);
        return distance > 0 && distance <= requiredDistance;
    }
}
