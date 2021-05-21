package io.github.vendas.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.github.vendas.validation.NotEmptyList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {
	
	@NotNull(message = "{campo.pedido.cliente.codigo.obrigatorio}")
	private Integer cliente;
	@NotNull(message = "{campo.pedido.total.obrigatorio}")
	private BigDecimal total;
	@NotEmptyList(message = "{campo.pedido.itens.obrigatorio=}")
	private List<ItemPedidoDTO> itens;

}
