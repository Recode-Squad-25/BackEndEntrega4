package com.herokuapp.colorebackend.Colore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.herokuapp.colorebackend.Colore.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	@Transactional(readOnly=true)
	Usuario findByEmail(String email);

}
