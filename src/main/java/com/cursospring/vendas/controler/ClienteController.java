package com.cursospring.vendas.controler;


import com.cursospring.vendas.model.Cliente;
import com.cursospring.vendas.repository.ClienteRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;


/*======!!!ResponseEntity representa toda a resposta HTTP: código de status, cabeçalhos e corpo!!!=====
*           Se usarmos a anotação @ResponseStatus não precisa retornar response Entity
* Resumindo o ResponseStatus é uma forma melhor de enviar status e throw, consegue customizar melhor a resposta de status etc*/

@RestController //Anotação expecializada em controller se usarmos ela, não precisamos usar o @ReponseBody, porque automaticamente ela já vem inserida
                // Anotação com @controller ai sim terá que usar a @ReponseBody
@RequestMapping("/api/clientes")
@Api("Api Clientes")
public class ClienteController {


    /*@RequestMapping(value = "/hello/{nome}",
                    method = RequestMethod.GET,
                    consumes = {"application/json","application/xml"},//Tipo de objeto que ele irá poder mandar,
                    produces = {"application/json", "application/xml"}//Tipo de objeto que irá retornar confome o application/type na requisição
                    )
    @ResponseBody
    public String helloClientes(@PathVariable("nome") String nomeCliente){
        return  String.format("Hello %s",nomeCliente);
    }*/

    @Autowired
    private ClienteRepository clienteRepository;
    @ApiOperation("Obter detalhes de um cliente")
    @ApiResponses({
            @ApiResponse(code = 200,message = "Cliente encontrado"),
            @ApiResponse(code = 404,message = "Cliente não encontrado para o ID informado")
    })
    @GetMapping("/{id}")
    //Se o nome do parametr da url for diferente então coloque @PathVariable("nomeParamsURL" para atribuir no Intergir id)
    public Cliente getClienteById(@PathVariable @ApiParam("id do cliente") Integer id) {

        return clienteRepository.findById(id)
                         .orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente não encontrado"));

    }

    @ApiOperation("Salvar um novo cliente")
    @ApiResponses({
            @ApiResponse(code = 201,message = "Cliente salvo com sucesso"),
            @ApiResponse(code = 404,message = "Erro de validação")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //Irá enviar como padrão este status 201
    public Cliente registrar(@RequestBody @Valid Cliente cliente){
       return clienteRepository.save(cliente);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)//Se for sucesso -> STATUS 204
    public void delete (@PathVariable  Integer id){

        clienteRepository.findById(id)
                .map(cliente ->{
                    clienteRepository.deleteById(cliente.getId());
                    return cliente;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não encontrado"));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)//Se for sucesso -> STATUS 204
    public Cliente update(@RequestBody @Valid Cliente cliente){

        return clienteRepository.findById(cliente.getId()).map(clienteExiste -> {
            clienteRepository.save(cliente);
            return clienteExiste;

        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente Não encontrado"));

    }

    @GetMapping()
    public List<Cliente> find( Cliente filtro){ //Se eu não colocar @RequestBody, ele irá fazer a pesquisa pela query na url exemplo http://localhost:8080/api/clientes?nome=Abner, mas se eu colocar ai ele vai pegar pelo json
        ExampleMatcher matcher = ExampleMatcher
                                 .matching()
                                 .withIgnoreCase()///Pode ser Maiuscula ou Minuscula
                                 .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro,matcher);
        return clienteRepository.findAll(example);


    }


}


