package com.herokuapp.colorebackend.Colore.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.herokuapp.colorebackend.Colore.dto.UsuarioDTO;
import com.herokuapp.colorebackend.Colore.dto.UsuarioNewDTO;
import com.herokuapp.colorebackend.Colore.models.Usuario;
import com.herokuapp.colorebackend.Colore.service.UsuarioService;



@RestController
@RequestMapping(value="/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Usuario> find(@PathVariable Integer id) {
		Usuario obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value="/email", method=RequestMethod.GET)
	public ResponseEntity<Usuario> find(@RequestParam(value="value") String email) {
		Usuario obj = service.findByEmail(email);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioNewDTO objDto) {
		Usuario obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody UsuarioDTO objDto, @PathVariable Integer id) {
		Usuario obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<UsuarioDTO>> findAll() {
		List<Usuario> list = service.findAll();
		List<UsuarioDTO> listDto = list.stream().map(obj -> new UsuarioDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	 
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<UsuarioDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Usuario> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<UsuarioDTO> listDto = list.map(obj -> new UsuarioDTO(obj));  
		return ResponseEntity.ok().body(listDto);
	}	
}