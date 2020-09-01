package com.cursospring.vendas.controler;

import com.cursospring.vendas.model.Cliente;
import com.cursospring.vendas.model.Produto;
import com.cursospring.vendas.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto registrarProduto(@RequestBody @Valid Produto produto){
        return produtoRepository.save(produto);

    }

    @GetMapping("/{id}")
    public Produto getClienteById(@PathVariable Integer id){
        return  produtoRepository.findById(id)
                                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND
                                                      ,"Produto não encotrado"));

    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Produto atualizar(@RequestBody @Valid Produto produto){
        return produtoRepository.findById(produto.getId())
                                .map(produtoExiste -> {
                                     return produtoRepository.save(produto);
                                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete (@PathVariable Integer id){
        produtoRepository.findById(id).map(produto -> {
            produtoRepository.deleteById(produto.getId());
            return Void.TYPE;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Produto não encontrado"));
    }



    @GetMapping()
    public List<Produto> find(Produto filtro){ //Se eu não colocar @RequestBody, ele irá fazer a pesquisa pela query na url exemplo http://localhost:8080/api/clientes?nome=Abner, mas se eu colocar ai ele vai pegar pelo json
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()///Pode ser Maiuscula ou Minuscula
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro,matcher);
        return produtoRepository.findAll(example);


    }


}
