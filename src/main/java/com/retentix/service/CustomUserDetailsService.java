
package com.retentix.service;

import com.retentix.entity.Sentiment;
import com.retentix.entity.User;
import com.retentix.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with email: " + email
                        )
                );

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }

    @Service
    public static class SentimentAnalysis {

        private final List<String> positiveWords = List.of(
                "good", "great", "excellent", "helpful",
                "happy", "satisfied", "love", "amazing",
                "fast", "wonderful", "easy", "perfect"
        );

        private final List<String> negativeWords = List.of(
                "bad", "poor", "terrible", "delay",
                "delayed", "angry", "unhappy", "hate",
                "slow", "broken", "worst", "disappointed"
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
}
