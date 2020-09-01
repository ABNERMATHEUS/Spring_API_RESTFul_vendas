package com.cursospring.vendas.controler;

import com.cursospring.vendas.dto.AtualizacaoStatusPedidoDTO;
import com.cursospring.vendas.dto.InformacaoItemPedidoDTO;
import com.cursospring.vendas.dto.InformacaoPedidoDTO;
import com.cursospring.vendas.dto.PedidoDTO;
import com.cursospring.vendas.model.ItemPedido;
import com.cursospring.vendas.model.Pedido;
import com.cursospring.vendas.model.StatusPedido;
import com.cursospring.vendas.service.impl.PedidoServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

    @Autowired
    private PedidoServiceimpl pedidoServiceimpl;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Integer registrar(@RequestBody @Valid PedidoDTO pedidoDTO){
       Pedido pedido =  pedidoServiceimpl.salvar(pedidoDTO);
       return pedido.getId();
    }

    @GetMapping("/{id}")
    public InformacaoPedidoDTO getPedido (@PathVariable  Integer id){
        return pedidoServiceimpl.obterPedidoCompleto(id)
                                .map(pedido -> converter(pedido))
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Pedido não encontrado"));
    }


    private InformacaoPedidoDTO converter (Pedido pedido){
       return InformacaoPedidoDTO.builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDatapedido().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .items(converter(pedido.getItensPedidos()))
               .status(pedido.getStatus().name())
                .build();

    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> items){
        if(CollectionUtils.isEmpty(items)){
            return Collections.emptyList();
        }
        return items.stream().map(itemPedido -> InformacaoItemPedidoDTO.builder()
                                                .descricao(itemPedido.getProduto().getDescricao())
                                                 .quantidade(itemPedido.getQuantidade())
                                                .precoUnitario(itemPedido.getProduto().getPreco())
                                                 .build()).collect(Collectors.toList());

    }

    @PatchMapping("/{id}") //apenas atualizar uma informação
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Integer id,@RequestBody AtualizacaoStatusPedidoDTO dto){
        String status = dto.getNovaStatus();
        pedidoServiceimpl.atualizaStatus(id, StatusPedido.valueOf(status));
    }



}
