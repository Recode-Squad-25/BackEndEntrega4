package com.herokuapp.colorebackend.Colore.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.colore.models.Cidade;
import br.com.colore.models.Endereco2;
import br.com.colore.models.Estado;
import br.com.colore.models.Usuario;
import br.com.colore.models.enums.Perfil;
import br.com.colore.models.enums.TipoUsuario;
import br.com.colore.repositories.CidadeRepository;
import br.com.colore.repositories.Endereco2Repository;
import br.com.colore.repositories.EstadoRepository;
import br.com.colore.repositories.UsuarioRepository;

@Service
public class DBService {

	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private Endereco2Repository enderecoRepository;
	

	public void instantiateDatabase() {

		Estado estado1 = new Estado(null, "Pernambuco");
		Estado estado2 = new Estado(null, "Bahia");

		Cidade cidade1 = new Cidade(null, "Recife", estado1);
		Cidade cidade2 = new Cidade(null, "Salvador", estado2);
		Cidade cidade3 = new Cidade(null, "Garanhuns", estado1);

		estado1.getCidades().addAll(Arrays.asList(cidade1, cidade3));
		estado2.getCidades().addAll(Arrays.asList(cidade2));

		estadoRepository.saveAll(Arrays.asList(estado1, estado2));
		cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));

		Usuario usuario1 = new Usuario(null, "Patrica Brandão", "coloreprojeto1@gmail.com", "12312312312",TipoUsuario.PESSOAFISICA, pe.encode("1234"));
		usuario1.getTelefones().addAll(Arrays.asList("37621742", "96192575"));
		usuario1.addPerfil(Perfil.USER);
		
		Usuario usuario2 = new Usuario(null, "Ana Brandão", "ana@gmail.com", "31628382740", TipoUsuario.PESSOAFISICA, pe.encode("123"));
		usuario2.getTelefones().addAll(Arrays.asList("93883321", "34252625"));
		usuario2.addPerfil(Perfil.ADMIN);
		usuario2.addPerfil(Perfil.USER);
		
		Endereco2 endereco1 = new Endereco2(null, "Rua Carlos Costa", "20", "Apto 201", "Pina", "50720190", usuario1,
				cidade1);
		Endereco2 endereco2 = new Endereco2(null, "Rua Olodum", "1267", "Apto 802", "Barra", "80720310", usuario1,
				cidade2);
		Endereco2 endereco3 = new Endereco2(null, "Avenida Floriano", "2106", null, "Centro", "281777012", usuario2, cidade1);

		usuario1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
		usuario2.getEnderecos().addAll(Arrays.asList(endereco3));

		usuarioRepository.saveAll(Arrays.asList(usuario1, usuario2));
		enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2, endereco3));
	}
}
