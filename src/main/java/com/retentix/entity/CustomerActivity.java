package com.retentix.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer_activity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @Builder.Default
    private int totalPurchases = 0;

    private LocalDateTime lastPurchaseDate;

    @Builder.Default
    private int totalLogins = 0;

    private LocalDateTime lastLoginDate;

    @Builder.Default
    private int totalSupportRequests = 0;

    @Builder.Default
    private LocalDateTime lastActivityDate = LocalDateTime.now();
}