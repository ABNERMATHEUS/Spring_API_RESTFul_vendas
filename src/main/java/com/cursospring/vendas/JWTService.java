package com.cursospring.vendas;

import com.cursospring.vendas.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@Service
public class JWTService {
    @Value("${security.jwt.expiracao}") //pegando o valor da chave application.properties
    private String expiracao;
    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken(Usuario usuario){
        long expiracaoString = Long.valueOf(expiracao);
        LocalDateTime dataHoraExpiracao=LocalDateTime.now().plusMinutes(expiracaoString);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        HashMap<String,Object> claims = new HashMap<>();
        claims.put("id",usuario.getLogin());
        claims.put("senha",usuario.getPassword());
        claims.put("roles",usuario.isAdmin()?"ADMIN":"USER");

        return Jwts.builder()
                .setSubject(usuario.getLogin())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.ES512,chaveAssinatura)
                .compact();
    }

    private Claims objerClaims (String token) throws ExpiredJwtException {
        return Jwts.parser()
                    .setSigningKey(chaveAssinatura)
                    .parseClaimsJws(token)
                    .getBody();
    }


    public boolean tokenValido(String token){
        try{
           Claims claims =  objerClaims(token);
           Date dataexpiracao = claims.getExpiration();
           LocalDateTime date = dataexpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
           return LocalDateTime.now().isAfter(date);
        }catch (Exception e){
            return false;
        }
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException{
        return (String) objerClaims(token).getSubject();
    }


}
