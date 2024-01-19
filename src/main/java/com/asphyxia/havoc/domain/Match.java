package com.asphyxia.havoc.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    private LocalDate date;
    @ManyToOne
    private User firstPlayer;
    @ManyToOne
    private User secondPlayer;
    @ManyToOne
    private Game game;
    @OneToOne
    private MatchResult result;
}
