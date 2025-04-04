package com.projetospring.projetoSpring.jogo; // Define o pacote onde esta classe está localizada.

import jakarta.persistence.Entity; // Importa a anotação para marcar a classe como uma entidade do JPA.
import jakarta.persistence.GeneratedValue; // Importa a anotação para gerar valores automaticamente.
import jakarta.persistence.Id; // Importa a anotação que define o atributo como chave primária.

import java.util.UUID; // Importa a classe UUID para identificadores únicos.

/**
 * Define que esta classe é uma entidade JPA que será mapeada para uma tabela no banco de dados.
 * O nome da tabela será "tb_jogos".
 */
@Entity(name = "tb_jogos")
public class JogoModel {

    @Id // Define o campo como chave primária da tabela.
    @GeneratedValue(generator = "UUID") // Gera um UUID automaticamente para o campo.
    private UUID idJogo; // Identificador único do jogo.

    private String nome;   // Nome do jogo.
    private String senha;  // Pode representar uma "senha" para acessar o jogo (depende da regra de negócio).
    private String genero; // Gênero do jogo (ex: ação, aventura, esportes, etc.).

    private UUID id; // Pode representar o ID do usuário dono/criador do jogo (chave estrangeira, dependendo da lógica).

    // Métodos getters e setters:

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(UUID idJogo) {
        this.idJogo = idJogo;
    }
}
