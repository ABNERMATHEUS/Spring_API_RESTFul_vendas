package com.cursospring.vendas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication  ///SpringBootServletInitializer para virar uma aplicacação web, depois que configurou o xml - tomcat (pom.xml)
public class VendasModulo3Application extends SpringBootServletInitializer {



    public static void main(String[] args) {
        SpringApplication.run(VendasModulo3Application.class, args);


    }

}
