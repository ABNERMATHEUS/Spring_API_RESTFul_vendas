package com.cursospring.vendas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
/*EU POSSO CRIAR GET E SET ESPECIAIS DENTRO DA CLASSE, O LOMBOK NÃO VAI RECLAMAR NÃO*/
@Entity
//@Table(name = "cliente") SE O NOME DA SUA INTIDADE FOR DIFERENTE DA SUA TABELA ENTÃO COLOCA @TABLE
@Getter //Ele irá criar todos os gets da classe utilizando o lombok
@Setter //Ele irá criar todos os set da classe utilizando o lombok
public class Cliente {

    public Cliente() {

    }

    public Cliente(String nome) {
        this.nome = nome;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") //SE O NOME DA TABELA NÃO FOR IGUAL DA VARIAVEL ENTÃO COLOCA @COLUMN
    private Integer id;

    @Column(name = "nome", length = 100)
    @NotEmpty(message = "{campo.nome.obrigatorio}")
    private String nome;

    @NotEmpty(message = "{campo.cpf.obrigatorio}")
    @CPF(message = "CPF inválido")
    @Column(name = "cpf",length = 11)
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    //Set quer dizer que ele não pode repetir pedidos //FetchType.EAGER <- quando eu chamar o cliente ele já retornaria automaticamente
    @JsonIgnore ///Permite que eu não enviei no meu get cliente
    @OneToMany(mappedBy = "cliente",fetch = FetchType.LAZY)//colocar o nome para mapear,o nome é do atributo que conecta um delas na classe Pedido(Cliente cliente está na classe Pedido) //Um cliente para muitos pedidos
    private Set<Pedido> pedidos;


}
