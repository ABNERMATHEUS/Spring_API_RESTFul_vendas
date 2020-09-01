package com.cursospring.vendas.exception;

public class PedidoNaoEncontrado extends RuntimeException {
    public PedidoNaoEncontrado() {
        super("Pedido não encontrado.");
    }
}
