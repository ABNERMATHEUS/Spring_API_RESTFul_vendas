package com.cursospring.vendas.service;

import com.cursospring.vendas.dto.AtualizacaoStatusPedidoDTO;
import com.cursospring.vendas.dto.PedidoDTO;
import com.cursospring.vendas.model.Pedido;
import com.cursospring.vendas.model.StatusPedido;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id,StatusPedido statusPedido);
}
