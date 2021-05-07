package ru.rplaton.labforward.coding.challenge;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.rplaton.labforward.coding.challenge.dto.AnalyticsResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TextAnalyticsIntegrationTest {

    public static final String ANALYSIS_PATH = "/text/analysis?word={word}&text={text}";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCorrectSearchResponse() {
        AnalyticsResult expected = new AnalyticsResult(1, Set.of("TesT", "aTest"));
        Map<String, String> params = new HashMap<>();
        params.put("word", "Test");
        params.put("text", "Test tRest TesT aTest");
        final ResponseEntity<AnalyticsResult> responseEntity = restTemplate.getForEntity(ANALYSIS_PATH, AnalyticsResult.class, params);
        final HttpStatus statusCode = responseEntity.getStatusCode();
        assertEquals(HttpStatus.OK, statusCode);
        final AnalyticsResult analyticsResult = responseEntity.getBody();
        assertEquals(expected, analyticsResult);
    }

    @Test
    void testAccents() {
        AnalyticsResult expected = new AnalyticsResult(1, Set.of("şŞğĞİıçÇ1ÖüÜ"));
        Map<String, String> params = new HashMap<>();
        params.put("word", "şŞğĞİıçÇöÖüÜ");
        params.put("text", "şŞğĞİıçÇöÖüÜ şŞğĞİıçÇ1ÖüÜ aşŞğĞİıçÇöÖüÜE");
        final ResponseEntity<AnalyticsResult> responseEntity = restTemplate.getForEntity(ANALYSIS_PATH, AnalyticsResult.class, params);
        final HttpStatus statusCode = responseEntity.getStatusCode();
        assertEquals(HttpStatus.OK, statusCode);
        final AnalyticsResult analyticsResult = responseEntity.getBody();
        assertEquals(expected, analyticsResult);
    }

    @ParameterizedTest
    @CsvSource({",Test test test test", "Test test,Test test test test", "'',Test", "'   ', TestT"})
    void testIllegalWordArguments(String word, String text) {
        Map<String, String> params = new HashMap<>();
        params.put("word", word);
        params.put("text", text);
        final ResponseEntity<String> responseEntity = restTemplate.getForEntity(ANALYSIS_PATH, String.class, params);
        final HttpStatus statusCode = responseEntity.getStatusCode();
        assertEquals(HttpStatus.BAD_REQUEST, statusCode);
        assertEquals("{\"message\":\"Only one word is supported for analyze and it should not be blank.\"}", responseEntity.getBody());
    }

    @ParameterizedTest
    @CsvSource({"Word,", "Word,''", "Word,'  '"})
    void testIllegalTextArguments() {
        Map<String, String> params = new HashMap<>();
        params.put("word", "Test ");
        params.put("text", "");
        final ResponseEntity<String> responseEntity = restTemplate.getForEntity(ANALYSIS_PATH, String.class, params);
        final HttpStatus statusCode = responseEntity.getStatusCode();
        assertEquals(HttpStatus.BAD_REQUEST, statusCode);
        assertEquals("{\"message\":\"Text is not presented to the analysis.\"}", responseEntity.getBody());

    }
}
