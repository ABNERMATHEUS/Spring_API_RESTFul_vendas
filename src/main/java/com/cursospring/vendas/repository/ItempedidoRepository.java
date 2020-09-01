package com.cursospring.vendas.repository;

import com.cursospring.vendas.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItempedidoRepository extends JpaRepository<ItemPedido,Integer> {


}
