package com.asphyxia.havoc.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Match {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime dateTime;
    @ManyToOne
    private Member firstPlayer;
    @ManyToOne
    private Member secondPlayer;
    @ManyToOne
    private Game game;
    @OneToOne(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private MatchResult result;
    @OneToOne(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private Declaration declaration;
}
