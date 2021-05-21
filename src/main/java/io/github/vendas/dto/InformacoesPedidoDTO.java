package io.github.vendas.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InformacoesPedidoDTO {
	
	private Integer codigo;
	private String data;
	private String cpf;
	private String nomeCliente;
	private BigDecimal totalPedido;
	private String status;
	private List<InformacoesItensDTO> itens;

}
