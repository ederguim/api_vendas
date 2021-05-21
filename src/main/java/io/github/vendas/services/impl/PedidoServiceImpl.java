package io.github.vendas.services.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import io.github.vendas.domain.Cliente;
import io.github.vendas.domain.ItemPedido;
import io.github.vendas.domain.Pedido;
import io.github.vendas.domain.Produto;
import io.github.vendas.dto.InformacoesItensDTO;
import io.github.vendas.dto.InformacoesPedidoDTO;
import io.github.vendas.dto.ItemPedidoDTO;
import io.github.vendas.dto.PedidoDTO;
import io.github.vendas.enums.StatusPedido;
import io.github.vendas.exception.PedidoNaoEncontradoException;
import io.github.vendas.exception.RegraNegocioException;
import io.github.vendas.repository.ClienteRepository;
import io.github.vendas.repository.ItemPedidoRepository;
import io.github.vendas.repository.PedidoRepository;
import io.github.vendas.repository.ProdutoRepository;
import io.github.vendas.services.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	private PedidoRepository repository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ItemPedidoRepository itensPedidoRepository;

	@Override
	@Transactional
	public Pedido create(PedidoDTO dto) {
		Cliente cliente = clienteRepository.findById(dto.getCliente())
				.orElseThrow(() -> new RegraNegocioException("Código de cliente inválido"));

		Pedido pedido = new Pedido();
		pedido.setTotal(dto.getTotal());
		pedido.setDataPedido(LocalDate.now());
		pedido.setCliente(cliente);
		pedido.setStatus(StatusPedido.REALIZADO);

		List<ItemPedido> itens = convertItens(pedido, dto.getItens());
		repository.save(pedido);
		itensPedidoRepository.saveAll(itens);
		pedido.setItens(itens);
		return pedido;

	}

	private List<ItemPedido> convertItens(Pedido pedido, List<ItemPedidoDTO> itens) {
		if (itens.isEmpty()) {
			throw new RegraNegocioException("Por favor, preencha a lista de pedidos");
		}
		return itens.stream().map(dto -> {
			Produto produto = produtoRepository.findById(dto.getProduto())
					.orElseThrow(() -> new RegraNegocioException("Código de produto inválido"));
			ItemPedido item = new ItemPedido();
			item.setQuantidade(dto.getQuantidade());
			item.setPedido(pedido);
			item.setProduto(produto);
			return item;
		}).collect(Collectors.toList());
	}

	public InformacoesPedidoDTO getById(Integer id) {
		return repository//
				.findByIdFetchItens(id).map(pedido -> convertPedido(pedido))//
				.orElseThrow(() -> new PedidoNaoEncontradoException("Pedido não encontrado"));
	}

	private InformacoesPedidoDTO convertPedido(Pedido pedido) {
		
		return InformacoesPedidoDTO.builder()//
		.codigo(pedido.getId())//
		.data(pedido.getDataPedido()//
				.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))//
		.cpf(pedido.getCliente().getCpf())//
		.nomeCliente(pedido.getCliente().getNome())//
		.totalPedido(pedido.getTotal())//
		.status(pedido.getStatus().name())//
		.itens(convertListIntes(pedido.getItens())).build();
	}


	private List<InformacoesItensDTO> convertListIntes(List<ItemPedido> itens) {
		
		if (CollectionUtils.isEmpty(itens)) {
			return Collections.emptyList();
		}
		
		return itens.stream().map(item -> {
			return InformacoesItensDTO.builder()//
					.precoUnitario(item.getProduto().getPreco())//
					.descricaoProduto(item.getProduto().getDescricao())//
					.quantidade(item.getQuantidade()).build();
		}).collect(Collectors.toList());
	}
	
	@Override
	@Transactional
	public void updateStatus(Integer id, StatusPedido status) {
		repository.findById(id).map(pedido -> {
			pedido.setStatus(status);
			return repository.save(pedido);
		}).orElseThrow(() -> new PedidoNaoEncontradoException("Pedido não encontrado"));
		
	}

}
