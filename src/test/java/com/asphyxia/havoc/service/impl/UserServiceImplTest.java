package com.asphyxia.havoc.service.impl;

import com.asphyxia.havoc.domain.Member;
import com.asphyxia.havoc.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getById() {
        Member member = new Member();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(member));

        userService.getById(1L);

        verify(userRepository).findById(1L);
    }
}