
package com.retentix.service;

import com.retentix.entity.Sentiment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class SentimentAnalysis {

    private final List<String> positiveWords = List.of(
            "good",
            "great",
            "excellent",
            "helpful",
            "happy",
            "satisfied",
            "love",
            "amazing",
            "fast",
            "wonderful",
            "easy",
            "perfect"
    );

    private final List<String> negativeWords = List.of(
            "bad",
            "poor",
            "terrible",
            "delay",
            "delayed",
            "angry",
            "unhappy",
            "hate",
            "slow",
            "broken",
            "worst",
            "disappointed"
    );

    public Sentiment analyze(String text) {

        if (text == null || text.isBlank()) {
            return Sentiment.NEUTRAL;
        }

        String normalizedText =
                text.toLowerCase(Locale.ROOT);

        int positiveScore = 0;
        int negativeScore = 0;

        for (String word : positiveWords) {
            if (normalizedText.contains(word)) {
                positiveScore++;
            }
        }

        for (String word : negativeWords) {
            if (normalizedText.contains(word)) {
                negativeScore++;
            }
        }

        if (positiveScore > negativeScore) {
            return Sentiment.POSITIVE;
        }

        if (negativeScore > positiveScore) {
            return Sentiment.NEGATIVE;
        }

        return Sentiment.NEUTRAL;
    }
}
