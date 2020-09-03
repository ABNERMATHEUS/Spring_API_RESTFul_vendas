package com.cursospring.vendas.controler;

import com.cursospring.vendas.dto.CredenciaisDTO;
import com.cursospring.vendas.dto.TokenDTO;
import com.cursospring.vendas.exception.SenhaInvalidaException;
import com.cursospring.vendas.model.Usuario;
import com.cursospring.vendas.security.jwt.JWTService;
import com.cursospring.vendas.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UserService userService;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JWTService jwtService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Usuario salvar(@RequestBody @Valid Usuario usuario){
        String senhaCriptografada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(senhaCriptografada);
        return  userService.salvar(usuario);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciaisDTO){
        try {
            Usuario usuario = Usuario.builder().login(credenciaisDTO.getLogin())
                                                    .password(credenciaisDTO.getSenha())
                                                    .build();
            UserDetails usuarioAutenticado = userService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            System.out.print(token);
            return  new TokenDTO(usuario.getLogin(),token);
        }catch (UsernameNotFoundException | SenhaInvalidaException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,e.getMessage());
        }
    }



}
