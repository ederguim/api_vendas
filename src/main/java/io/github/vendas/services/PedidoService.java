package io.github.vendas.services;

import io.github.vendas.domain.Pedido;
import io.github.vendas.dto.InformacoesPedidoDTO;
import io.github.vendas.dto.PedidoDTO;
import io.github.vendas.enums.StatusPedido;

public interface PedidoService {

	Pedido create(PedidoDTO pedidoDTO);
	
	InformacoesPedidoDTO getById(Integer id);

	void updateStatus(Integer id, StatusPedido statusPedido);

}
