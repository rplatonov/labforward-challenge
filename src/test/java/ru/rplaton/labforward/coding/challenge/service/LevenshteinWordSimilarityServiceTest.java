package ru.rplaton.labforward.coding.challenge.service;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.junit.jupiter.api.Test;
import ru.rplaton.labforward.coding.challenge.service.impl.LevenshteinWordSimilarityService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LevenshteinWordSimilarityServiceTest {

    @Test
    void testSimilarityWithDefaultDistance() {
        WordSimilarityService ws = new LevenshteinWordSimilarityService(new LevenshteinDistance(1),
                1);
        assertTrue(ws.isSimilar("Word", "WorD"));
        assertFalse(ws.isSimilar("Word", "WordAB"));
        assertTrue(ws.isSimilar("abcde", "ábcde"));
        assertFalse(ws.isSimilar("abcde", "abcde"));
    }

    @Test
    void testSimilarityWithExtendedDistance() {
        WordSimilarityService ws = new LevenshteinWordSimilarityService(new LevenshteinDistance(2),
                2);
        assertTrue(ws.isSimilar("Word", "WorD"));
        assertTrue(ws.isSimilar("Word", "WorDD"));
        assertFalse(ws.isSimilar("Www", "WwwwwW"));
    }
}
