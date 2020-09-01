package com.cursospring.vendas.service.impl;

import com.cursospring.vendas.dto.AtualizacaoStatusPedidoDTO;
import com.cursospring.vendas.dto.ItempedidoDTO;
import com.cursospring.vendas.dto.PedidoDTO;
import com.cursospring.vendas.exception.PedidoNaoEncontrado;
import com.cursospring.vendas.exception.RegraNegocioException;
import com.cursospring.vendas.model.*;
import com.cursospring.vendas.repository.ClienteRepository;
import com.cursospring.vendas.repository.ItempedidoRepository;
import com.cursospring.vendas.repository.PedidoRepository;
import com.cursospring.vendas.repository.ProdutoRepository;
import com.cursospring.vendas.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceimpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;


    @Autowired
    private ItempedidoRepository itempedidoRepository;

    @Override
    @Transactional //Ou acontece tudo com sucesso ou se dar algum erro, ele irá dar um rollback
    public Pedido salvar(PedidoDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getCliente()).orElseThrow(() -> new RegraNegocioException("Código cliente inválido"));
        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDatapedido(LocalDate.now());
        pedido.setCliente(cliente);
        List<ItemPedido> itemPedidos = converterItems(pedido, dto.getItems());
        pedidoRepository.save(pedido);
        itempedidoRepository.saveAll(itemPedidos);
        pedido.setItensPedidos(itemPedidos);
        pedido.setStatus(StatusPedido.REALIZADO);
        return pedido;
    }

    private  List<ItemPedido> converterItems(Pedido pedido,List<ItempedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }
        return items.stream().map(itempedidoDTO -> {
            Produto produto = produtoRepository.findById(itempedidoDTO.getProduto())
                             .orElseThrow(() -> new RegraNegocioException("Código de produto inválido: "+itempedidoDTO.getProduto()));
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setQuantidade(itempedidoDTO.getQuantidade());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            return itemPedido;
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidoRepository.findByIdFetchItensPedidos(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id,StatusPedido statusPedido) {
        pedidoRepository.findById(id).map(  pedido -> {
            pedido.setStatus(statusPedido);
            return pedidoRepository.save(pedido);
        }).orElseThrow(() -> new PedidoNaoEncontrado());
    }
}
