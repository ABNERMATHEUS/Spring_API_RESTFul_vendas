package com.cursospring.vendas.repository;

import com.cursospring.vendas.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Integer> {

    /*Tudo conforme a convenção QUERY METHODOS*/

    List<Cliente> findByNomeLike(String nome);
    ///COLOCAR OS PARAMETROS NA ORDEM VC DECLAROU O QUERY METHODO
    List<Cliente> findByNomeOrId(String nome, Integer id);

    List<Cliente> findByNomeOrIdOrderById(String nome, Integer id);


    /*Você pode modificar também os querys methodos
    colocando:
    @Query(<sua query>)
    @Modifying
    void deleteByNome(String nome);
     */



    /* POSSO ESCOLHER UMAS DA DUAS PARA CRIAR MINHAS QUERY*/
   // @Query(value = "select * from cliente c where c.nome = :nome",nativeQuery = true)///ESSA QUERY É NATIVA POSSO USAR ASSIM TAMBÉM
    @Query(value = "select c from Cliente c where c.nome = :nome") //usar esses dois pontos para o paramêtro :nome e colocar o @Param e nome que eu dei para a variavel com os dois pontos
    List<Cliente> encontrarporNome(@Param("nome") String nome);

    boolean existsByNome(String nome);

    @Query("select c from  Cliente c left join fetch c.pedidos where c.id =:id")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);





}
