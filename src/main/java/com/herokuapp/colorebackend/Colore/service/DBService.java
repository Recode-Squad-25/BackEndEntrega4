package com.herokuapp.colorebackend.Colore.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.herokuapp.colorebackend.Colore.model.enums.Perfil;
import com.herokuapp.colorebackend.Colore.model.enums.TipoUsuario;
import com.herokuapp.colorebackend.Colore.models.Cidade;
import com.herokuapp.colorebackend.Colore.models.Endereco;
import com.herokuapp.colorebackend.Colore.models.Endereco2;
import com.herokuapp.colorebackend.Colore.models.Estado;
import com.herokuapp.colorebackend.Colore.models.Usuario;
import com.herokuapp.colorebackend.Colore.repository.CidadeRepository;
import com.herokuapp.colorebackend.Colore.repository.Endereco2Repository;
import com.herokuapp.colorebackend.Colore.repository.EstadoRepository;
import com.herokuapp.colorebackend.Colore.repository.UsuarioRepository;



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
		Estado estado3 = new Estado(null, "São Paulo");
		Estado estado4 = new Estado(null, "Rio de Janeiro");
		Estado estado5 = new Estado(null, "Ceará");
		Estado estado6 = new Estado(null, "Rio Grande do Sul");

		Cidade cidade1 = new Cidade(null, "Recife", estado1);
		Cidade cidade2 = new Cidade(null, "Salvador", estado2);
		Cidade cidade3 = new Cidade(null, "Garanhuns", estado1);
		Cidade cidade4 = new Cidade(null, "São Paulo", estado3);
		Cidade cidade5 = new Cidade(null, "Niterói", estado4);
		Cidade cidade6 = new Cidade(null, "Fortaleza", estado5);
		Cidade cidade7 = new Cidade(null, "Porto Alegre", estado6);

		estado1.getCidades().addAll(Arrays.asList(cidade1, cidade3));
		estado2.getCidades().addAll(Arrays.asList(cidade2));
		estado3.getCidades().addAll(Arrays.asList(cidade4));
		estado4.getCidades().addAll(Arrays.asList(cidade5));
		estado5.getCidades().addAll(Arrays.asList(cidade6));
		estado6.getCidades().addAll(Arrays.asList(cidade7));

		estadoRepository.saveAll(Arrays.asList(estado1, estado2, estado3, estado4, estado5, estado6));
		cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3, cidade4, cidade5, cidade6, cidade7));

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

		usuario1.getEnderecos().add((Endereco) Arrays.asList(endereco1, endereco2));
		usuario2.getEnderecos().add((Endereco) Arrays.asList(endereco3));

		usuarioRepository.saveAll(Arrays.asList(usuario1, usuario2));
		enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2, endereco3));
	}
}
