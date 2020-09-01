package com.cursospring.vendas.repository;

import com.cursospring.vendas.model.Cliente;
import com.cursospring.vendas.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Integer> {

    List<Pedido> findByCliente(Cliente cliente);

    @Query("select p from Pedido p left join fetch p.itensPedidos where p.id =:id")
    Optional<Pedido> findByIdFetchItensPedidos(Integer id);
}
