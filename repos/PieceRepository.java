package com.kstoi.web_chess.repos;

import com.kstoi.web_chess.models.entities.Piece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PieceRepository extends JpaRepository<Piece, Long> {
}