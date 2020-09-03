package com.cursospring.vendas.service.impl;

import com.cursospring.vendas.exception.SenhaInvalidaException;
import com.cursospring.vendas.model.Usuario;
import com.cursospring.vendas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private  PasswordEncoder encoder;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Transactional //Como estamos em uma camada de servico vamos colocar @TRANSACTIONAL
    public Usuario salvar(Usuario usuario){
        return usuarioRepository.save(usuario);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       /**
        * Criando sem base de dados
        *
        * if(!username.equals("Abner")){
            throw new UsernameNotFoundException("Usuário não encontrado na base");
        }
        return User.builder()
                .username("Abner")
                .password(encoder.encode("321"))
                .roles("USER","ADMIN")
                .build();
        **/

       Usuario usuario = usuarioRepository.findByLogin(username).orElseThrow(() ->  new UsernameNotFoundException("Usuário não encontrado na base"));
       String[] roles = usuario.isAdmin() ? new String[]{"ADMIN,USER"}:new String[]{"USER"};

       return User.builder()
               .username(usuario.getLogin())
               .password(usuario.getPassword()) // nesse momento não tem necessidades criptografar, pq no banco já estará
               .roles(roles)
               .build();

    }



    public UserDetails autenticar(Usuario usuario){
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean senhasBatem = encoder.matches(usuario.getPassword(),user.getPassword());
        if(senhasBatem){
            return user;
        }
        throw new SenhaInvalidaException();
    }

}
