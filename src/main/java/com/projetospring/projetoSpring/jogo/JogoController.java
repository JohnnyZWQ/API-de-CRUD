package com.projetospring.projetoSpring.jogo;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/jogo")
public class JogoController {

    @Autowired
    private IJogoRepository jogoRepository;

    @PostMapping("/novo")
    private ResponseEntity criarJogo(@RequestBody JogoModel jogoModel, HttpServletRequest request){
        var jogoExiste = this.jogoRepository.findByNome(jogoModel.getNome());
        if(jogoExiste != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("usuario cadastrado");
        }else {
            var senhaHash = BCrypt.withDefaults()
                    .hashToString(12, jogoModel.getSenha().toCharArray());
            jogoModel.setSenha(senhaHash);
            var criado = this.jogoRepository.save(jogoModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(criado);
        }
    }

    @GetMapping("/listarjogos")
    public List<JogoModel> listarJogos() {
        List<JogoModel> jogoCad = jogoRepository.findAll();
        return jogoCad;
    }

    @PutMapping("/atualizarjogo")
    public ResponseEntity atualizaJogo(@RequestBody JogoModel jogoModel) {
        var criado = this.jogoRepository.save(jogoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @DeleteMapping("/deleteuser/{id}")
    public void deletaUser(@PathVariable UUID id){
        jogoRepository.deleteById(id);
    }

}
