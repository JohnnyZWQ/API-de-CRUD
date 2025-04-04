package com.projetospring.projetoSpring.jogo; 
import org.springframework.data.jpa.repository.JpaRepository; 
import java.util.UUID; 

/*
 * Interface responsável pela comunicação com o banco de dados para a entidade JogoModel.
 * Estende JpaRepository, que já fornece métodos prontos como save, findAll, deleteById, etc.
 */
public interface IJogoRepository extends JpaRepository<JogoModel, UUID> {

    /*
     * Método personalizado que retorna um jogo pelo nome.
     * Spring Data JPA entende o nome "findByNome" e cria a consulta automaticamente.
     */
    JogoModel findByNome(String nome);
}
