package com.encuesta.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.encuesta.entity.Usuario;
import com.encuesta.repository.UsuarioRepository;


@Service
public class EncuestaUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario user = usuarioRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}		
		return new EncuestaUserDetails(user.getId(), user.getUsername(), user.getPassword(), user.getIdRol().getNombreRol());
	}

}