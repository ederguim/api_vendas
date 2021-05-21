package io.github.vendas.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.vendas.domain.Pedido;
import io.github.vendas.dto.AtualizacaoStatusPedidoDTO;
import io.github.vendas.dto.InformacoesPedidoDTO;
import io.github.vendas.dto.PedidoDTO;
import io.github.vendas.enums.StatusPedido;
import io.github.vendas.services.PedidoService;

@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

	@Autowired
	private PedidoService service;

	@PostMapping("create")
	@ResponseStatus(HttpStatus.CREATED)
	public Pedido create(@RequestBody @Valid PedidoDTO dto) {
		return service.create(dto);
	}
	
	@GetMapping(value = "{id}")
	public InformacoesPedidoDTO findById(@PathVariable("id") Integer id) {
		return service.getById(id);
	}

	@PatchMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateStatus(@PathVariable("id") Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto) {
		service.updateStatus(id, StatusPedido.valueOf(dto.getNovoStatus()));
	}

}
