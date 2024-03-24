package com.asphyxia.havoc.dto.responses;

import com.asphyxia.havoc.domain.Declaration;
import lombok.Builder;

import java.util.List;

@Builder
public record DeclarationResponse(
        Long id,
        String winner,
        String loser,
        String game,
        Long matchId,
        String image,
        String status) {
    public static DeclarationResponse fromDeclaration(Declaration declaration, String url) {
        return DeclarationResponse.builder()
                .id(declaration.getId())
                .winner(declaration.getMember().getUsername())
                .loser(declaration.getMatch().getFirstPlayer().getUsername().equals(declaration.getMember().getUsername()) ?
                        declaration.getMatch().getSecondPlayer().getUsername() :
                        declaration.getMatch().getFirstPlayer().getUsername())
                .matchId(declaration.getMatch().getId())
                .game(declaration.getMatch().getGame().getName())
                .image(url + declaration.getImage())
                .status(declaration.getMatch().getResult() == null ? "PENDING" : "APPROVED")
                .build();
    }

    public static List<DeclarationResponse> fromDeclarations(List<Declaration> declarations, String url){
        return declarations.stream()
                .map(declaration -> fromDeclaration(declaration, url))
                .toList();
    }
}
