package io.github.vendas.services;

import java.util.List;

import io.github.vendas.domain.Produto;

public interface ProdutoService {

	Produto save(Produto produto);

	Produto findById(Integer id);

	void delete(Integer id);

	void update(Integer id, Produto produto);

	List<Produto> find(Produto filtro);

}
