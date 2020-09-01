package com.cursospring.vendas.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter //Ele irá criar todos os gets da classe utilizando o lombok
@Setter //Ele irá criar todos os set da classe utilizando o lombok
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne //Muitos pedidos tem um clientes
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "data_pedido")
    private LocalDate datapedido;

    @Column(length = 20,precision = 20,scale = 2) //scale =  duas casas depois da virgula
    private BigDecimal total;

    @OneToMany(mappedBy ="pedido"  )//Colocar o nome da chave que é seu atributo da classe no mappedBy !! GERAR GETTERS E SETTERS !!
    private List<ItemPedido> itensPedidos;

    @Enumerated(EnumType.STRING)//EnumType.Ordinal ira salvar a posição 0 ou 1 do enum
    private StatusPedido status;

}
