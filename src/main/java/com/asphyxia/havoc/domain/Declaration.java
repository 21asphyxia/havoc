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
public class Declaration {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private Match match;
    @ManyToOne
    private Member member;
    private String image;

}
