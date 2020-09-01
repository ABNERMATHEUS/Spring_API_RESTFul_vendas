package com.cursospring.vendas.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter //Ele irá criar todos os gets da classe utilizando o lombok
@Setter //Ele irá criar todos os set da classe utilizando o lombok
public class ItempedidoDTO {
    private Integer produto;
    private Integer quantidade;
}
