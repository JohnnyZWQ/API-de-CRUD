package com.projetospring.projetoSpring.curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICursoRepository extends JpaRepository<CursoModel, UUID> {
    CursoModel findByIdUser(UUID idUser);  //
}
