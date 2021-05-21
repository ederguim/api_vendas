package io.github.vendas.services;

import java.util.List;

import io.github.vendas.domain.Cliente;

public interface ClienteService {

	Cliente save(Cliente cliente);

	Cliente findById(Integer id);

	void delete(Integer id);

	void update(Integer id, Cliente cliente);

	List<Cliente> find(Cliente filtro);

}
