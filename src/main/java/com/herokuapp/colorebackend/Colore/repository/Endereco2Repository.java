package com.herokuapp.colorebackend.Colore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.colore.models.Endereco2;

@Repository
public interface Endereco2Repository extends JpaRepository<Endereco2, Integer> {

}
