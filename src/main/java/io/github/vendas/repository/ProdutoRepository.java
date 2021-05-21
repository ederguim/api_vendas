package io.github.vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.vendas.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	
	

}
