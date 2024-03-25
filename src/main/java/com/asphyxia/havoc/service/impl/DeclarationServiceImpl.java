package com.asphyxia.havoc.service.impl;

import com.asphyxia.havoc.domain.Declaration;
import com.asphyxia.havoc.domain.Match;
import com.asphyxia.havoc.domain.MatchResult;
import com.asphyxia.havoc.domain.Member;
import com.asphyxia.havoc.repository.DeclarationRepository;
import com.asphyxia.havoc.service.DeclarationService;
import com.asphyxia.havoc.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeclarationServiceImpl implements DeclarationService {
    private final DeclarationRepository declarationRepository;
    private final MatchServiceImpl matchService;
    private final ImageService imageService;
    private final GameEloServiceImpl gameEloService;

    @Override
    public List<Declaration> getAllDeclarations() {
        return declarationRepository.findAll();
    }

    @Override
    public Declaration getDeclarationById(Long id) {
        return declarationRepository.findById(id).orElseThrow(() -> new RuntimeException("Declaration not found"));
    }

    @Override
    public boolean existsByMatch(Match match) {
        return declarationRepository.existsByMatch(match);
    }

    @Override
    public Declaration save(Long matchId, MultipartFile image) {
        Match match = matchService.getMatchById(matchId);
        if (existsByMatch(match)) throw new IllegalArgumentException("Declaration already exists");

        Member loggedInMember = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String dir = "src/main/resources/static/images/declarations";
        String imageUrl = null;
        try {
            imageUrl = imageService.saveImageToStorage(dir, image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Declaration declaration = Declaration.builder()
                .member(loggedInMember)
                .match(match)
                .image(imageUrl)
                .build();
        return declarationRepository.save(declaration);

    }

    @Override
    public Declaration approveDeclaration(Long declarationId) {
        Declaration declaration = getDeclarationById(declarationId);
        MatchResult result = MatchResult.builder()
                .winnerScore(1)
                .loserScore(0)
                .eloGain(10)
                .eloLoss(8)
                .winner(declaration.getMember())
                .match(declaration.getMatch())
                .build();
        Match match = declaration.getMatch();
        match.setResult(result);
        matchService.updateMatch(match);
        Member loser = match.getFirstPlayer().equals(result.getWinner()) ? match.getSecondPlayer() : match.getFirstPlayer();
        gameEloService.updateElo(match.getGame(), result.getWinner(), loser, result);
        return declaration;
    }
}
