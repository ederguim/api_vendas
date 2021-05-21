package io.github.vendas.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.vendas.domain.Usuario;
import io.github.vendas.dto.CredenciaisDTO;
import io.github.vendas.exception.SenhaInvalidaException;
import io.github.vendas.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UsuarioRepository repository;
	
	public UserDetails autenticar(CredenciaisDTO usuario) {
		UserDetails user = loadUserByUsername(usuario.getLogin());
		boolean senhas = encoder.matches(usuario.getSenha(), user.getPassword());
		if(senhas) {
			return user;
		}
		throw new SenhaInvalidaException();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = repository.findByLogin(username)//
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados"));

		String[] roles = usuario.isAdmin() ? new String[] { "ADMIN", "USER" } : new String[] { "USER" };

		return User//
				.builder()//
				.username(usuario.getLogin())//
				.password(usuario.getSenha())//
				.roles(roles).build();

	}

	public Usuario create(Usuario usuario) {
		String senha = encoder.encode(usuario.getSenha());
		usuario.setSenha(senha);
		return repository.save(usuario);
	}
	
	
	
//	Usuario usuario = Usuario//
//			.builder()//
//			.login(dto.getLogin())//
//			.senha(senha)//
//			.admin(dto.isAdmin()).build();

}
