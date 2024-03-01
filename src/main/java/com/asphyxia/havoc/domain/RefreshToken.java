package com.asphyxia.havoc.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Member user;

    @Column(unique = true)
    private String token;

    private Instant expiryDate;

    private boolean valid = true;


}