package com.cursospring.vendas.validation.contraintValidation;

import com.cursospring.vendas.validation.NotEmptyList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Annotation;
import java.util.List;

public class NotEmptyListValidador implements ConstraintValidator<NotEmptyList, List> {
    @Override
    public boolean isValid(List value, ConstraintValidatorContext context) {
        return value != null && !value.isEmpty(); //Se não passar nessa condição ele irá lançar o erro
    }

    @Override
    public void initialize(NotEmptyList constraintAnnotation) {
        //constraintAnnotation.messagem();



    }
}
