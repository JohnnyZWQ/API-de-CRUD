package com.projetospring.projetoSpring.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/novo")
    private ResponseEntity criarUsuario(@RequestBody UserModel userModel, HttpServletRequest request){
        var usuarioExiste = this.userRepository.findByname(userModel.getName());
        if(usuarioExiste != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("usuario cadastrado");
        }else {
            var senhaHash = BCrypt.withDefaults()
                    .hashToString(12, userModel.getPassword().toCharArray());
            userModel.setPassword(senhaHash);
            var criado = this.userRepository.save(userModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(criado);
        }
    }

    @GetMapping("/usercadastrados")
    public List<UserModel> listarCursos() {
        List<UserModel> usuarioCad = userRepository.findAll();
        return usuarioCad;
    }

    @PutMapping("/atualiza")
    public ResponseEntity atualizaUser(@RequestBody UserModel userModel) {
        var criado = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @DeleteMapping("/deleteuser/{id}")
    public void deletaUser(@PathVariable UUID id){
        userRepository.deleteById(id);
    }

}
