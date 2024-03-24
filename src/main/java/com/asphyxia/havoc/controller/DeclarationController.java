package com.asphyxia.havoc.controller;

import com.asphyxia.havoc.domain.Match;
import com.asphyxia.havoc.dto.responses.DeclarationResponse;
import com.asphyxia.havoc.dto.responses.MatchResponse;
import com.asphyxia.havoc.service.impl.DeclarationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/declarations")
@RequiredArgsConstructor
public class DeclarationController {

    private final DeclarationServiceImpl declarationService;

    @Value("${server.port}")
    private String port;

    @GetMapping
    public ResponseEntity<List<DeclarationResponse>> getAll() {
        List<DeclarationResponse> declarations = DeclarationResponse.fromDeclarations(declarationService.getAllDeclarations(), "http://localhost:" + port + "/api/v1/images/declarations/");
        return new ResponseEntity<>(declarations, HttpStatus.OK);
    }

    @PostMapping(value = "/{matchId}", consumes = {"multipart/form-data"})
    public ResponseEntity<MatchResponse> saveDeclaration(@PathVariable Long matchId, @RequestParam("image") MultipartFile image) {
        Match match = declarationService.save(matchId, image).getMatch();
        return ResponseEntity.ok(MatchResponse.builder()
                .id(match.getId())
                .firstPlayer(match.getFirstPlayer().getUsername())
                .secondPlayer(match.getSecondPlayer().getUsername())
                .game(match.getGame().getName())
                .status("DECLARED")
                .build());
    }

    @PostMapping("/{declarationId}/approve")
    public ResponseEntity<DeclarationResponse> approveDeclaration(@PathVariable Long declarationId) {
        return ResponseEntity.ok(DeclarationResponse.fromDeclaration(declarationService.approveDeclaration(declarationId), "http://localhost:" + port + "/api/v1/images/declarations/"));
    }

}
