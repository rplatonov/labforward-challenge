package ru.rplaton.labforward.coding.challenge.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.rplaton.labforward.coding.challenge.dto.AnalyticsResult;

import javax.validation.ConstraintViolationException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(properties = {
        "text.analyzer.required.distance=1",
})
public class SimpleTextAnalyticsServiceTest {

    @Autowired
    TextAnalyticsService textAnalyticsService;

    @Test
    void testAnalyticsServiceOnExamplesFromTask() {
        Set<String> levenshteinMultiple = Set.of("Words", "Wor", "word");

        final AnalyticsResult similarWordAnalysis = textAnalyticsService.makeAnalyzeByWord("Word", "Word Words Wor word");
        final Integer frequency = similarWordAnalysis.getFrequency();
        final Set<String> similarWords = similarWordAnalysis.getSimilarWords();
        assertEquals(1, frequency);
        assertEquals(3, similarWords.size());
        assertEquals(levenshteinMultiple, similarWords);

        final AnalyticsResult frequencyAnalysis = textAnalyticsService.makeAnalyzeByWord("Word", "Word Word Word word");
        final Integer frequencyMoreThanOne = frequencyAnalysis.getFrequency();
        final Set<String> oneSimilarWord = frequencyAnalysis.getSimilarWords();
        assertEquals(3, frequencyMoreThanOne);
        assertEquals(1, oneSimilarWord.size());
        assertEquals(Set.of("word"), oneSimilarWord);
    }

    @Test
    void testNullText() {
        Throwable exception = assertThrows(ConstraintViolationException.class, () -> textAnalyticsService.makeAnalyzeByWord("Word", null));
        final String message = exception.getMessage();
        assertEquals("makeAnalyzeByWord.text: Text should not be blank.", message);
    }

    @Test
    void testBlankText() {
        Throwable exception = assertThrows(ConstraintViolationException.class, () -> textAnalyticsService.makeAnalyzeByWord("Word", ""));
        final String message = exception.getMessage();
        assertEquals("makeAnalyzeByWord.text: Text should not be blank.", message);
    }

    @Test
    void testNullWord() {
        Throwable exception = assertThrows(ConstraintViolationException.class, () -> textAnalyticsService.makeAnalyzeByWord(null, "Test me"));
        final String message = exception.getMessage();
        assertEquals("makeAnalyzeByWord.word: Only one word is supported for analyze and it should not be blank.", message);
    }

    @Test
    void testBlankWord() {
        Throwable exception = assertThrows(ConstraintViolationException.class, () -> textAnalyticsService.makeAnalyzeByWord(" ", "Test    Me"));
        final String message = exception.getMessage();
        assertEquals("makeAnalyzeByWord.word: Only one word is supported for analyze and it should not be blank.", message);
    }

    @Test
    void testTextWithPunctuationsSplitting() {
        final AnalyticsResult worldWordAnalysis = textAnalyticsService.makeAnalyzeByWord("World", "Hello, ? World!");
        final AnalyticsResult helloWorldAnalysis = textAnalyticsService.makeAnalyzeByWord("Hello", "Hello,%!234World!");

        assertEquals(1, worldWordAnalysis.getFrequency());
        assertEquals(1, helloWorldAnalysis.getFrequency());
    }

    @Test
    void testNonEnglishWords() {
        final AnalyticsResult wortAnalysis = textAnalyticsService.makeAnalyzeByWord("Ärger", "Das Wort \"Ärger\" wird mit einem großen Ä geschrieben. Ärge Arger Äger");
        assertEquals(1, wortAnalysis.getFrequency());
        assertEquals(3, wortAnalysis.getSimilarWords().size());

        final AnalyticsResult turkishWordAnalysis = textAnalyticsService.makeAnalyzeByWord("şŞğĞİıçÇÖüÜ", "şŞğĞİıç1ÖüÜ");
        assertEquals(0, turkishWordAnalysis.getFrequency());
        assertEquals(1, turkishWordAnalysis.getSimilarWords().size());

    }

    @Test
    void testNotExistingWord() {
        final AnalyticsResult notExistingWordAnalysisResult = textAnalyticsService.makeAnalyzeByWord("Apple", "Hello, World!");
        assertEquals(0, notExistingWordAnalysisResult.getFrequency());
        assertTrue(notExistingWordAnalysisResult.getSimilarWords().isEmpty());
    }


}
