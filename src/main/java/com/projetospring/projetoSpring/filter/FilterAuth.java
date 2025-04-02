package com.projetospring.projetoSpring.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.projetospring.projetoSpring.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterAuth extends OncePerRequestFilter {

    @Autowired
    IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var serveletpath = request.getServletPath();
        if(serveletpath.equals("/jogo/novo")) {
            var authorization = request.getHeader("Authorization");
            filterChain.doFilter(request,response);
            System.out.println(authorization);
            System.out.println("Authorization");

            var authEncode = authorization.substring("Basic".length()).trim();
            System.out.println("Authorization");
            System.out.println(authEncode);

            byte[] authDecode = Base64.getDecoder().decode(authEncode);
            System.out.println("Authorization");
            System.out.println(authDecode);

            var authString = new String(authDecode);
            System.out.println(authString);
            String[] credenciais = authString.split(":");
            String username = credenciais[0];
            String password = credenciais[1];
            System.out.println("username: " + username + " password: " + password);

            //VALIDAÇÃO DE USUARIO
            var user = this.userRepository.findByname(username);
            if(user==null){
                response.sendError(401,"Nao funciona, Usuario inexistente");
            }else {
                var senhaverifica= BCrypt.verifyer().verify(password.toCharArray(),user.getPassword());
                if (senhaverifica.verified){
                    request.setAttribute("id", user.getId());
                    filterChain.doFilter(request,response);
                }else {
                    response.sendError(401,"Senha Incorreta");
                }
            }
        } else {
            filterChain.doFilter(request,response);
        }
    }
}
