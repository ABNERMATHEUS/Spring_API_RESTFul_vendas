package com.cursospring.vendas.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Getter
public class ApiErros {

    private List<Object> errors;


    public ApiErros(String mesagemErro, Integer status) {
        this.errors = Collections.singletonList(mesagemErro);
        errors.add(status);
    }

    public ApiErros(List<Object> errors) {
        this.errors = errors;
    }
}
