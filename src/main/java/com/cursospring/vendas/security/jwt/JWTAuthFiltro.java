package com.cursospring.vendas.security.jwt;

import com.cursospring.vendas.service.impl.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Security;

public class JWTAuthFiltro extends OncePerRequestFilter {
    /**
     * classe criada para interceptar as requisições e obter o token do header Authorization,
     * verifica se é válido,e coloca dentro do contexto, para o securityConfig.class interceptar as requisições e colocar um usuário do token dentro do contexto do Spring Security
     */
    private JWTService jwtService;
    private UserService userService;

    public JWTAuthFiltro(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override //intersepitando uma requisição antes de tudo
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
                                    , FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        System.out.print(authorization);
        if(authorization!= null && authorization.startsWith("Bearer")){
            String token = authorization.split(" ")[1];
            boolean isValid = jwtService.tokenValido(token);
            if(isValid){
                String loginUsuario = jwtService.obterLoginUsuario(token);
                UserDetails usuario = userService.loadUserByUsername(loginUsuario);
                UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(user); //definimos dentro do contexto do spring security
            }
        }

        filterChain.doFilter(request,response);

    }
}
