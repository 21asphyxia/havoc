package com.asphyxia.havoc.repository;

import com.asphyxia.havoc.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByNameIgnoreCase(String name);

    boolean existsByNameLikeIgnoreCase(String name);
}
