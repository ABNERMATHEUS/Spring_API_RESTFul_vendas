package com.cursospring.vendas.dto;


import com.cursospring.vendas.validation.NotEmptyList;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * {
 * 	"cliente":1,
 * 	"total":100,
 * 	"items":[
 *                {
 * 			"produto":1,
 * 			"quantidade":10
 *        }
 * 	]
 *
 * }
 */

@Getter //Ele irá criar todos os gets da classe utilizando o lombok
@Setter //Ele irá criar todos os set da classe utilizando o lombok
public class PedidoDTO {

    @NotNull(message = "{campo.codigo-cliente.obrigatorio}")
    private Integer cliente;
    @NotNull(message = "{campo.total-pedido.obrigatorio}")
    private BigDecimal total;
    @NotEmptyList(message = "{campo.items-pedido.obrigatorio}")
    private List<ItempedidoDTO> items;
}
