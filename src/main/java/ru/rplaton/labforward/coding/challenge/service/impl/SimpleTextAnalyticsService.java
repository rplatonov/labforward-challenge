package ru.rplaton.labforward.coding.challenge.service.impl;

import org.apache.commons.lang3.mutable.MutableInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.rplaton.labforward.coding.challenge.dto.AnalyticsResult;
import ru.rplaton.labforward.coding.challenge.service.TextAnalyticsService;
import ru.rplaton.labforward.coding.challenge.service.WordSimilarityService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * HandMade text analytics service.
 */
@Service
public class SimpleTextAnalyticsService implements TextAnalyticsService {

    private final Logger logger = LoggerFactory.getLogger(SimpleTextAnalyticsService.class);

    private final WordSimilarityService similarityService;

    public SimpleTextAnalyticsService(WordSimilarityService similarityService) {
        this.similarityService = similarityService;
    }

    @Override
    public AnalyticsResult makeAnalyzeByWord(final String word, final String text) {
        final Map<String, MutableInt> textElements = new HashMap<>();

        logger.debug("Text analysis is started.");
        final String[] byWords = text.split("(?U)\\W+");
        for (final String textElem : byWords) {
            textElements.computeIfAbsent(textElem, w -> new MutableInt()).increment();
        }
        logger.debug("Text analysis has been ended");

        Integer frequency = 0;
        logger.debug("Analysis for word {} is started.", word);
        if (textElements.containsKey(word)) {
            frequency = textElements.get(word).getValue();
        }
        Set<String> candidates = new HashSet<>();
        for (String elem :
                textElements.keySet()) {
            if (similarityService.isSimilar(word, elem)) {
                candidates.add(elem);
            }
        }

        logger.debug("Analysis for word {} is ended.", word);

        return new AnalyticsResult(frequency, candidates);
    }
}
