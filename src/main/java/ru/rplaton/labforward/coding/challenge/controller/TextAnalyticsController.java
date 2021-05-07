package ru.rplaton.labforward.coding.challenge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.rplaton.labforward.coding.challenge.dto.AnalyticsResult;
import ru.rplaton.labforward.coding.challenge.dto.ApiError;
import ru.rplaton.labforward.coding.challenge.service.TextAnalyticsService;
import ru.rplaton.labforward.coding.challenge.validator.WordToAnalyze;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/text/analysis")
@Validated
public class TextAnalyticsController {

    private final TextAnalyticsService textAnalyticsService;

    public TextAnalyticsController(TextAnalyticsService textAnalyticsService) {
        this.textAnalyticsService = textAnalyticsService;
    }

    // Deprecated APPLICATION_JSON_UTF8_VALUE to make correct returns in the browser.
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Operation(method = "Make analysis by word in the text.", description = "Searching for a word frequency and similar words in the provided text element.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                            schema = @Schema(implementation = AnalyticsResult.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid parameters are presented.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_UTF8_VALUE,
                            schema = @Schema(implementation = ApiError.class)))})

    public AnalyticsResult makeAnalysisByWord(@Parameter(description = "Word to analyse. Should contain only one word and it could not be blank.")
                                              @RequestParam(name = "word")
                                              @WordToAnalyze final String word,
                                              @Parameter(description = "Notebook entry.")
                                              @RequestParam(name = "text")
                                              @NotBlank(message = "Text is not presented to the analysis.") final String text) {
        return textAnalyticsService.makeAnalyzeByWord(word, text);
    }
}
