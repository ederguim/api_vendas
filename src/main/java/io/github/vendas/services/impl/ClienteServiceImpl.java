package io.github.vendas.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import io.github.vendas.domain.Cliente;
import io.github.vendas.repository.ClienteRepository;
import io.github.vendas.services.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository repository;

	public Cliente create(Cliente cliente) {
		return repository.save(cliente);
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Cliente findById(Integer id) {
		Optional<Cliente> cliente = repository.findById(id);
		if (cliente.isPresent()) {
			return cliente.get();
		}
		return null;
	}

	public Cliente save(Cliente cliente) {
		return repository.save(cliente);
	}

	public void delete(Integer id) {
		Optional<Cliente> cliente = repository.findById(id);
		if (cliente.isPresent()) {
			repository.delete(cliente.get());
		}
	}

	public void update(Integer id, Cliente cliente) {
		Optional<Cliente> _cliente = repository.findById(id);
		if (_cliente.isPresent()) {
			cliente.setId(_cliente.get().getId());
			cliente = repository.saveAndFlush(cliente);
		}
	}

	public List<Cliente> find(Cliente filtro) {

		ExampleMatcher matcher = ExampleMatcher//
				.matching().withIgnoreCase()//
				.withStringMatcher(StringMatcher.CONTAINING);

		Example<Cliente> example = Example.of(filtro, matcher);
		List<Cliente> clientes = repository.findAll(example);
		return clientes;

	}

}
