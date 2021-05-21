package io.github.vendas.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.vendas.services.impl.UsuarioServiceImpl;

public class JwtAuthFilter extends OncePerRequestFilter {

	private JwtService service;

	private UsuarioServiceImpl usuarioService;

	public JwtAuthFilter(JwtService service, UsuarioServiceImpl usuarioService) {
		this.service = service;
		this.usuarioService = usuarioService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorization = request.getHeader("Authorization");
		if (authorization != null && authorization.startsWith("Bearer")) {
			String token = authorization.split(" ")[1];
			boolean isValid = service.tokenValido(token);
			if (isValid) {
				String usuarioLogado = service.obterLoginUsuario(token);
				UserDetails usuario = usuarioService.loadUserByUsername(usuarioLogado);
				UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(usuario, null,
						usuario.getAuthorities());
				user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(user);
			}
		}
		
		filterChain.doFilter(request, response);
	}
}
