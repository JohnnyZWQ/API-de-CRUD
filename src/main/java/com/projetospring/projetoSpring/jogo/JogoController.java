package com.projetospring.projetoSpring.jogo; // Define o pacote onde o controller está localizado.

import at.favre.lib.crypto.bcrypt.BCrypt; // Biblioteca para criptografar senhas com o algoritmo BCrypt.
import jakarta.servlet.http.HttpServletRequest; // Permite acesso à requisição HTTP (não está sendo usada no método).
import org.springframework.beans.factory.annotation.Autowired; // Anotação para injeção automática de dependência.
import org.springframework.http.HttpStatus; // Classe com os códigos de status HTTP.
import org.springframework.http.ResponseEntity; // Representa a resposta HTTP com corpo, cabeçalhos e status.
import org.springframework.web.bind.annotation.*; // Anotações para criar endpoints REST.

import java.util.List; // Classe para listas em Java.
import java.util.UUID; // Classe usada para identificadores únicos universais.

/**
 * Define que essa classe é um controller REST que escutará requisições a partir do endpoint /jogo.
 */
@RestController
@RequestMapping("/jogo")
public class JogoController {

    @Autowired // Injeta automaticamente uma instância do repositório de jogos.
    private IJogoRepository jogoRepository;

    /**
     * Endpoint POST para criar um novo jogo.
     * Se o nome do jogo já estiver cadastrado, retorna erro.
     * Caso contrário, criptografa a senha, salva o jogo no banco e retorna o jogo criado.
     */
    @PostMapping("/novo")
    private ResponseEntity criarJogo(@RequestBody JogoModel jogoModel, HttpServletRequest request) {
        var jogoExiste = this.jogoRepository.findByNome(jogoModel.getNome()); // Verifica se já existe um jogo com o mesmo nome.

        if (jogoExiste != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("usuario cadastrado"); // Retorna erro se o nome já estiver em uso.
        } else {
            // Criptografa a senha do jogo com BCrypt.
            var senhaHash = BCrypt.withDefaults()
                    .hashToString(12, jogoModel.getSenha().toCharArray());
            jogoModel.setSenha(senhaHash); // Substitui a senha original pela criptografada.

            var criado = this.jogoRepository.save(jogoModel); // Salva o jogo no banco.
            return ResponseEntity.status(HttpStatus.CREATED).body(criado); // Retorna o jogo criado com status 201.
        }
    }

    /**
     * Endpoint GET para listar todos os jogos cadastrados.
     * @return Lista com todos os jogos do banco.
     */
    @GetMapping("/listarjogos")
    public List<JogoModel> listarJogos() {
        List<JogoModel> jogoCad = jogoRepository.findAll(); // Busca todos os registros da tabela de jogos.
        return jogoCad;
    }

    /**
     * Endpoint PUT para atualizar um jogo.
     * Reescreve o jogo no banco de dados com as novas informações.
     */
    @PutMapping("/atualizarjogo")
    public ResponseEntity atualizaJogo(@RequestBody JogoModel jogoModel) {
        var criado = this.jogoRepository.save(jogoModel); // Salva a atualização no banco.
        return ResponseEntity.status(HttpStatus.CREATED).body(criado); // Retorna o jogo atualizado.
    }

    /**
     * Endpoint DELETE para excluir um jogo pelo ID.
     * @param id UUID do jogo a ser deletado.
     */
    @DeleteMapping("/deleteuser/{id}")
    public void deletaUser(@PathVariable UUID id) {
        jogoRepository.deleteById(id); // Deleta o jogo do banco com base no ID fornecido.
    }
}
