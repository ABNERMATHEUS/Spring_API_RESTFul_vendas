package com.cursospring.vendas.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/*Lombok gerá os get e set quando criar a application em produção*/

@Entity
//@Table(name = "produto") POSSO COLOCAR SE A TABELA FOR DIFERENTE DO NOME DA CLASSE
@Getter //Ele irá criar todos os gets da classe utilizando o lombok
@Setter //Ele irá criar todos os set da classe utilizando o lombok
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@Column(name = "descricao") POSSO COLOCAR SE A COLUNA FOR DIFERENTE DO NOME DO ATRIBUTO
    @Column
    @NotEmpty(message = "{campo.descricao.obrigatorio}")
    private  String descricao;

    @Column(name = "preco_unitario")
    @NotNull(message = "{campo.preco.obrigatorio}")
    private BigDecimal preco;

}
