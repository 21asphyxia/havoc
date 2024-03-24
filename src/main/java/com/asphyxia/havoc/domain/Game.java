package com.asphyxia.havoc.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Game {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String image;
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<GameElo> elos;
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Match> matches;
}
