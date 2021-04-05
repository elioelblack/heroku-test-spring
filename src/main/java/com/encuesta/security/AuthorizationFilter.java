package com.encuesta.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
	public static final String TOKEN_BEARER_PREFIX = "Bearer ";
	
	private String tokenSecret;
	
	public AuthorizationFilter(String tokenSecret, AuthenticationManager authManager) {
		super(authManager);
		this.tokenSecret = tokenSecret;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("req "+req);
		System.out.println("res "+res);
		System.out.println("chain "+chain);
		String header = req.getHeader(HEADER_AUTHORIZACION_KEY);
		if (header == null || !header.startsWith(TOKEN_BEARER_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);		
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
		if (token != null) {
			
			final JwtParser jwtParser = Jwts.parser().setSigningKey(tokenSecret);
			final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""));
			final Claims claims = claimsJws.getBody();
			final String user = claims.getSubject();
			
			final Collection<SimpleGrantedAuthority> authorities =
					Arrays.stream(claims.get("roles").toString().split(","))
							.map(SimpleGrantedAuthority::new)
							.collect(Collectors.toList());
						

			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, authorities);
			}
			
			return null;
		}
		
		return null;
	}
	
}
