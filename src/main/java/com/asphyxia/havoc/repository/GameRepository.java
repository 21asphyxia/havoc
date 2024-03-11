package com.asphyxia.havoc.repository;

import com.asphyxia.havoc.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
