package io.github.vendas.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import io.github.vendas.domain.Produto;
import io.github.vendas.repository.ProdutoRepository;
import io.github.vendas.services.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoRepository repository;

	public Produto create(Produto produto) {
		return repository.save(produto);
	}

	public List<Produto> findAll() {
		return repository.findAll();
	}

	public Produto findById(Integer id) {
		Optional<Produto> produto = repository.findById(id);
		if (produto.isPresent()) {
			return produto.get();
		}
		return null;
	}

	public Produto save(Produto produto) {
		return repository.save(produto);
	}

	public void delete(Integer id) {
		Optional<Produto> produto = repository.findById(id);
		if (produto.isPresent()) {
			repository.delete(produto.get());
		}
	}

	public void update(Integer id, Produto produto) {
		Optional<Produto> _produto = repository.findById(id);
		if (_produto.isPresent()) {
			produto.setId(_produto.get().getId());
			produto = repository.saveAndFlush(produto);
		}
	}

	public List<Produto> find(Produto filtro) {

		ExampleMatcher matcher = ExampleMatcher//
				.matching().withIgnoreCase()//
				.withStringMatcher(StringMatcher.CONTAINING);

		Example<Produto> example = Example.of(filtro, matcher);
		List<Produto> produtos = repository.findAll(example);
		return produtos;

	}

}
