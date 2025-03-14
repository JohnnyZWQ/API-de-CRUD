package com.projetospring.projetoSpring.jogo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IJogoRepository extends JpaRepository<JogoModel, UUID> {
    JogoModel findByNome(String nome);
}
