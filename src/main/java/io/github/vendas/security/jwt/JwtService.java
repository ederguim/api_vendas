package io.github.vendas.security.jwt;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.github.vendas.domain.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

	@Value("${security.jwt.expiracao}")
	private String expiracao;

	@Value("${security.jwt.chave.assinatura}")
	private String chaveAssinatura;

	public String gerarToken(Usuario usuario) {
		long expString = Long.valueOf(expiracao);
		LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
		Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
		Date data = Date.from(instant);

		return Jwts.builder()//
				.setSubject(usuario.getLogin())//
				.setExpiration(data)//
				.signWith(SignatureAlgorithm.HS512, chaveAssinatura)//
				.compact();
	}

	private Claims obterClaims(String token) throws ExpiredJwtException {
		return Jwts//
				.parser()//
				.setSigningKey(chaveAssinatura)//
				.parseClaimsJws(token)//
				.getBody();
	}

	public boolean tokenValido(String token) {

		try {
			Date expiration = obterClaims(token).getExpiration();
			LocalDateTime data = expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			return !LocalDateTime.now().isAfter(data);
		} catch (Exception e) {
			return false;
		}

	}
	
	public String obterLoginUsuario(String token) throws ExpiredJwtException {
		return (String) obterClaims(token).getSubject();
	}

//	public static void main(String[] args) {
//		ConfigurableApplicationContext run = SpringApplication.run(VendasApplication.class);
//		JwtService bean = run.getBean(JwtService.class);
//		Usuario usuario = Usuario.builder().login("fulano").build();
//		String gerarToken = bean.gerarToken(usuario);
//		System.out.println(gerarToken);
//		boolean valido = bean.tokenValido(gerarToken);
//		System.out.println("Token valido? " + valido);
//		System.out.println(bean.obterLoginUsuario(gerarToken));
//		
//	}
}
