package ru.rplaton.labforward.coding.challenge.configuration;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LevenshteinDistanceProvider {

    final Integer threshold;

    public LevenshteinDistanceProvider(@Value("${text.analyzer.required.distance}") Integer threshold) {
        this.threshold = threshold;
    }

    @Bean
    public LevenshteinDistance getLevenshteinDistance() {
        return threshold != null ? new LevenshteinDistance(threshold) : LevenshteinDistance.getDefaultInstance();
    }
}
