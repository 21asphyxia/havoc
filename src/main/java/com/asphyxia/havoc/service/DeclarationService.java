package com.asphyxia.havoc.service;

import com.asphyxia.havoc.domain.Declaration;
import com.asphyxia.havoc.domain.Match;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DeclarationService {
    List<Declaration> getAllDeclarations();

    Declaration getDeclarationById(Long id);

    boolean existsByMatch(Match match);

    Declaration save(Long matchId, MultipartFile image);

    Declaration approveDeclaration(Long declarationId);
}
