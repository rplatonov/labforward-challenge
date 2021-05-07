package ru.rplaton.labforward.coding.challenge.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.rplaton.labforward.coding.challenge.dto.AnalyticsResult;
import ru.rplaton.labforward.coding.challenge.service.TextAnalyticsService;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TextAnalyticsController.class)
public class TextAnalyticsControllerTest {

    public static final String ANALYSIS_PATH = "/text/analysis";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TextAnalyticsService mockAnalyticsService;

    @Test
    void testCorrectSearch() throws Exception {
        final AnalyticsResult analyticsResultStub = new AnalyticsResult(1, Set.of("TesT", "aTest"));
        when(mockAnalyticsService.makeAnalyzeByWord(anyString(), anyString()))
                .thenReturn(analyticsResultStub);
        mockMvc.perform(get(ANALYSIS_PATH)
                .param("word", "Test")
                .param("text", "Test tRest TesT aTest"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"frequency\":1,\"similarWords\":[\"aTest\",\"TesT\"]}"));
    }

    @Test
    void testAccents() throws Exception {
        final AnalyticsResult analyticsResult = new AnalyticsResult(1, Collections.singleton("şŞğĞİıçÇöÖüÜ"));
        when(mockAnalyticsService.makeAnalyzeByWord(anyString(), anyString()))
                .thenReturn(analyticsResult);
        mockMvc.perform(get(ANALYSIS_PATH)
                .param("word", "şŞğĞİıçÇöÖüÜ")
                .param("text", "şŞğĞİıçÇöÖüÜ şŞğĞİıçÇ1ÖüÜ aşŞğĞİıçÇöÖüÜE"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"frequency\":1,\"similarWords\":[\"şŞğĞİıçÇöÖüÜ\"]}"));
    }

    @Test
    void testEmptyWord() throws Exception {
        mockMvc.perform(get(ANALYSIS_PATH)
                .param("word", "")
                .param("text", "Word Word Word"))
                .andDo(result -> {
                    assertEquals(400, result.getResponse().getStatus());
                    assertEquals("{\"message\":\"Only one word is supported for analyze and it should not be blank.\"}", result.getResponse().getContentAsString());
                    assertTrue(result.getResolvedException() instanceof ConstraintViolationException);
                    assertNotNull(result.getResolvedException());
                });
    }

    @Test
    void testMoreThanOneWord() throws Exception {
        mockMvc.perform(get(ANALYSIS_PATH)
                .param("word", "Word Word")
                .param("text", "Word Word Word"))
                .andDo(result -> {
                    assertEquals(400, result.getResponse().getStatus());
                    assertEquals("{\"message\":\"Only one word is supported for analyze and it should not be blank.\"}", result.getResponse().getContentAsString());
                    assertTrue(result.getResolvedException() instanceof ConstraintViolationException);
                    assertNotNull(result.getResolvedException());
                });
    }

    @Test
    void testEmptyText() throws Exception {
        mockMvc.perform(get(ANALYSIS_PATH)
                .param("word", "123")
                .param("text", "    "))
                .andDo(result -> {
                    assertEquals(400, result.getResponse().getStatus());
                    assertEquals("{\"message\":\"Text is not presented to the analysis.\"}", result.getResponse().getContentAsString());
                    assertTrue(result.getResolvedException() instanceof ConstraintViolationException);
                    assertNotNull(result.getResolvedException());
                });
    }
}
