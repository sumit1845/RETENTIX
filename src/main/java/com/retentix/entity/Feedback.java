
package com.retentix.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Customer who submitted the feedback
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    // Type of feedback: TEXT, AUDIO, or VIDEO
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FeedbackType feedbackType;

    // Text feedback or transcript generated from audio/video
    @Column(columnDefinition = "TEXT")
    private String content;

    // Sentiment result: POSITIVE, NEGATIVE, or NEUTRAL
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Sentiment sentiment;

    // Location of the uploaded audio/video file
    private String mediaUrl;

    // Feedback submission date and time
    @Builder.Default
    private LocalDateTime submittedAt = LocalDateTime.now();
}
