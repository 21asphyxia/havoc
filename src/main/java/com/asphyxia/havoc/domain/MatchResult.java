package com.asphyxia.havoc.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Member winner;
    private Integer eloGain;
    private Integer eloLoss;
    private Integer winnerScore;
    private Integer loserScore;
}