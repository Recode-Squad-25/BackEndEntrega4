package com.herokuapp.colorebackend.Colore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.herokuapp.colorebackend.Colore.models.Usuario;
import com.herokuapp.colorebackend.Colore.repository.UsuarioRepository;
import com.herokuapp.colorebackend.Colore.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Usuario usu = repo.findByEmail(email);
		if (usu == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new UserSS(usu.getId(), usu.getEmail(), usu.getSenha(), usu.getPerfis());
	}
	
}
