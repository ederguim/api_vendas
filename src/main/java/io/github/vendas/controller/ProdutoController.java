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

import io.github.vendas.domain.Produto;
import io.github.vendas.services.ProdutoService;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

	@Autowired
	private ProdutoService service;

	@PostMapping("create")
	@ResponseStatus(HttpStatus.CREATED)
	public Produto create(@RequestBody @Valid Produto produto) {
		return service.save(produto);
	}

	@GetMapping(value = "{id}")
	public Produto findById(@PathVariable("id") Integer id) {
		return service.findById(id);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Integer id) {
		service.delete(id);
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable("id") Integer id, @RequestBody @Valid Produto produto) {
		service.update(id, produto);
	}

	@GetMapping
	public List<Produto> find(Produto filtro) {
		return service.find(filtro);
	}

}
