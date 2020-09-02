package com.cursospring.vendas.config;

import com.cursospring.vendas.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class securityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        /*!O QUE ACONTECE POR TRÁS DOS PANOS!!!!
        PasswordEncoder passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) { //charSequence = senha do usuário
                return charSequence+"321";
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) { //charSequence= senha user, s= senha criptografada
                return (charSequence+"321").equals(s);
            }
        }*/
            //USAR ESTE -> BCryptPasswordEncoder Ele gera um hash diferente
        return  new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       /**
        * Usuário em memória
        * auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
                                    .withUser("Abner")
                                    .password(passwordEncoder().encode("321"))
                                    .roles("USER");
        **/

       auth.userDetailsService(userService)
           .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // desabilitar, porque estamos usando como uma api
            .authorizeRequests()
                .antMatchers("/api/clientes/**")
                    .hasAnyRole("USER","ADMIN")
                .antMatchers("/api/pedidos/**")
                    .hasAnyRole("USER","ADMIN")
                .antMatchers("/api/produto/**")
                    .hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/usuarios/**") //Somente está liberado para qualquer um cadastrar um usuario (APENAS O POST)
                    .permitAll()
                .anyRequest().authenticated() //Se tiver outra url, precisa no mínimo estar autenticado
                .and() //and() ele volta para raiz do objeto
                                //  .permitAll() Qualquer usuário pode acessar
                // .formLogin(); Ele cria um formulário de login ou criar uma page login e colocar o caminho  dentro ();
                .httpBasic(); //Vamos poder fazer requisição através do header
    }


}
