package ru.rplaton.labforward.coding.challenge.dto;

import java.util.Objects;
import java.util.Set;

public class AnalyticsResult {
    private final Integer frequency;
    private final Set<String> similarWords;

    public AnalyticsResult(Integer frequency, Set<String> similarWords) {
        this.frequency = frequency;
        this.similarWords = similarWords;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public Set<String> getSimilarWords() {
        return similarWords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnalyticsResult that = (AnalyticsResult) o;
        return Objects.equals(frequency, that.frequency) && Objects.equals(similarWords, that.similarWords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(frequency, similarWords);
    }
}
