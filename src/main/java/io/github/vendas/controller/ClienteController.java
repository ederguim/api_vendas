package io.github.vendas.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.vendas.domain.Cliente;
import io.github.vendas.services.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/cliente")
@Api("Api Clientes")
public class ClienteController {

	@Autowired
	private ClienteService service;

	@PostMapping("create")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation("Criar um novo cliente")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cliente criado com sucesso"),
		@ApiResponse(code = 404, message = "Erro de validação ao criar o cliente"),
	})
	public Cliente create(@RequestBody @Valid Cliente cliente) {
		return service.save(cliente);
	}

	@GetMapping(value = "{id}")
	@ApiOperation("Obtem detalhes do cliente")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cliente encontrado"),
		@ApiResponse(code = 404, message = "Cliente não encontrado"),
	})
	public Cliente findById(@PathVariable("id") @ApiParam("Id do cliente") Integer id) {
		return service.findById(id);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Integer id) {
		service.delete(id);
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable("id") Integer id, @RequestBody @Valid Cliente cliente) {
		service.update(id, cliente);
	}

	@GetMapping
	public List<Cliente> find(Cliente filtro) {
		return service.find(filtro);
	}

}
