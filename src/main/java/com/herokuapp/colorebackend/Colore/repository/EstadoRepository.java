package com.herokuapp.colorebackend.Colore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.colore.models.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer>{
	@Transactional(readOnly=true)
	public List<Estado> findAllByOrderByNome();
}
