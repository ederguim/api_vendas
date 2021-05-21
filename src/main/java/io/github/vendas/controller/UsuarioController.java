package io.github.vendas.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.vendas.domain.Usuario;
import io.github.vendas.dto.CredenciaisDTO;
import io.github.vendas.dto.TokenDTO;
import io.github.vendas.exception.SenhaInvalidaException;
import io.github.vendas.security.jwt.JwtService;
import io.github.vendas.services.impl.UsuarioServiceImpl;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioServiceImpl usuarioServiceImpl;
	@Autowired
	private JwtService JwtService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario create(@RequestBody @Valid Usuario usuario) {
		return usuarioServiceImpl.create(usuario);
	}

	@PostMapping("/auth")
	public TokenDTO autenticar(@RequestBody CredenciaisDTO dto) {
		try {
			UserDetails usuarioAutenticado = usuarioServiceImpl.autenticar(dto);
			Usuario usuario = Usuario.builder()//
					.login(usuarioAutenticado.getUsername())//
					.senha(usuarioAutenticado.getPassword())//
					.build();
			String token = JwtService.gerarToken(usuario);
			return new TokenDTO(usuario.getLogin(), token);
		} catch (UsernameNotFoundException | SenhaInvalidaException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}

}
