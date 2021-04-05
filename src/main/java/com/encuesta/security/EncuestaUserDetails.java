package com.encuesta.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class EncuestaUserDetails implements UserDetails {

	private static final long serialVersionUID = 5155720064139820502L;

	private int id;
	private String username;
	private String password;
	private String id_institucion;
	private Collection<? extends GrantedAuthority> authorities;
	
	public EncuestaUserDetails() {

	}

	/*
	 * public SaphrivUserDetails(String id, String username, String password, String
	 * role, String id_institucion) { this.id = id; this.username = username;
	 * this.password = password;
	 * 
	 * 
	 * List<SimpleGrantedAuthority> authorities = new
	 * ArrayList<SimpleGrantedAuthority>(); authorities.add(new
	 * SimpleGrantedAuthority(role)); authorities.add(new
	 * SimpleGrantedAuthority(id_institucion));
	 * 
	 * this.authorities = authorities; //this.id_institucion = id_institucion; }
	 */
	public EncuestaUserDetails(int id, String username, String password, String role) {
		this.id = id;
		this.username = username;
		this.password = password;
		

		System.out.println("rol en metodo"+role);
		List<SimpleGrantedAuthority> authorities = new
		ArrayList<SimpleGrantedAuthority>(); 
		authorities.add(new SimpleGrantedAuthority(role)); 		 

		this.authorities = authorities;
		
		
	}

	public int getId() {
		return id;
	}
	
	public String getId_institucion() {
		return id_institucion;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
	public void setId_institucion(String id_institucion) {
		this.id_institucion = id_institucion;
	}

}
