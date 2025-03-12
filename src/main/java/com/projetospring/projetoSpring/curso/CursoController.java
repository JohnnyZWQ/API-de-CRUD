package com.projetospring.projetoSpring.curso;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    private ICursoRepository cursoRepository;

    @PostMapping("/criar")
    private ResponseEntity criar(@RequestBody CursoModel curso, HttpServletRequest request) {
        var cursoExistente = this.cursoRepository.findByIdUser(curso.getIdUser());
        if (cursoExistente != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já existe um curso associado a este usuário");
        } else {
            System.out.println("TESTE O SISTEMA CHEGOU AQUI");
            var criado = this.cursoRepository.save(curso);
            return ResponseEntity.status(HttpStatus.CREATED).body(criado);
        }
    }

    @GetMapping("/teste")
    public String testar() {
        return "API do curso está rodando!";
    }
}
