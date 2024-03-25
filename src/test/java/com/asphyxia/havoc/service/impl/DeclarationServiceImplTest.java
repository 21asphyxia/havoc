package com.asphyxia.havoc.service.impl;

import com.asphyxia.havoc.domain.Declaration;
import com.asphyxia.havoc.repository.DeclarationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeclarationServiceImplTest {

    @Mock
    private DeclarationRepository declarationRepository;

    @InjectMocks
    private DeclarationServiceImpl declarationService;

    @Test
    public void getByInvalidId() {
        when(declarationRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> declarationService.getDeclarationById(1L));
    }
    @Test
    public void getByValidId() {
        when(declarationRepository.findById(1L)).thenReturn(Optional.of(new Declaration()));
        assertEquals(Declaration.class, declarationService.getDeclarationById(1L).getClass());
    }
}
