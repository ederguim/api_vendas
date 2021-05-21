package io.github.vendas.controller;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class ApiErrors {

	@Getter
	private List<String> erros;

	public ApiErrors(String messageError) {
		this.erros = Arrays.asList(messageError);
	}

	public ApiErrors(List<String> erros) {
		this.erros = erros;
	}

}
