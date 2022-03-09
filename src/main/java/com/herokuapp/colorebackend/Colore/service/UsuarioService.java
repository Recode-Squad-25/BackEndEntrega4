package com.herokuapp.colorebackend.Colore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.colore.controllers.exceptions.DataIntegrityException;
import br.com.colore.dto.UsuarioDTO;
import br.com.colore.dto.UsuarioNewDTO;
import br.com.colore.models.Cidade;
import br.com.colore.models.Endereco2;
import br.com.colore.models.Usuario;
import br.com.colore.models.enums.Perfil;
import br.com.colore.models.enums.TipoUsuario;
import br.com.colore.repositories.Endereco2Repository;
import br.com.colore.repositories.UsuarioRepository;
import br.com.colore.security.UserSS;
import br.com.colore.services.exceptions.AuthorizationException;
import br.com.colore.services.exceptions.ObjectNotFoundException;


@Service
public class UsuarioService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private UsuarioRepository repo;
	
	@Autowired
	private Endereco2Repository enderecoRepository;

	public Usuario find(Integer id) {
		
		UserSS user = UserService.authenticated();
		if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Usuario> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
		}
	
	@Transactional
	public Usuario insert(Usuario obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	
	public Usuario update(Usuario obj) {
		Usuario newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir");
		}
	}
	
	public List<Usuario> findAll() {
		return repo.findAll();
	}
	
	public Usuario findByEmail(String email) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
	
		Usuario obj = repo.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Usuario.class.getName());
		}
		return obj;
	}
	
	public Page<Usuario> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Usuario fromDTO(UsuarioDTO objDto) {
		return new Usuario(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
		
	}
	
	public Usuario fromDTO(UsuarioNewDTO objDto) {
		Usuario usu = new Usuario(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoUsuario.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco2 end = new Endereco2(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), usu, cid);
		usu.getEnderecos().add(end);
		usu.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			usu.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			usu.getTelefones().add(objDto.getTelefone3());
		}
		return usu;
	}
	
	private void updateData(Usuario newObj, Usuario obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
		
}
