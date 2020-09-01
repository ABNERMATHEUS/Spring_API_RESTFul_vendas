package com.cursospring.vendas.validation;

import com.cursospring.vendas.validation.contraintValidation.NotEmptyListValidador;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //rodar em tempo de execução
@Target(ElementType.FIELD) //Marcar o campo
@Constraint(validatedBy = NotEmptyListValidador.class) //Validar o campo
public @interface NotEmptyList {
    String message() default "a lista não pode estar vazia.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
