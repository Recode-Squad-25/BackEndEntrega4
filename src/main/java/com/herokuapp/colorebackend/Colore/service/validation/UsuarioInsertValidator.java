package com.herokuapp.colorebackend.Colore.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.herokuapp.colorebackend.Colore.controller.exception.FieldMessage;
import com.herokuapp.colorebackend.Colore.dto.UsuarioNewDTO;
import com.herokuapp.colorebackend.Colore.model.enums.TipoUsuario;
import com.herokuapp.colorebackend.Colore.models.Usuario;
import com.herokuapp.colorebackend.Colore.repository.UsuarioRepository;
import com.herokuapp.colorebackend.Colore.service.validation.utils.BR;

public class UsuarioInsertValidator implements ConstraintValidator<UsuarioInsert, UsuarioNewDTO> {

	@Autowired
	private UsuarioRepository repo;

	@Override
	public void initialize(UsuarioInsert ann) {
	}

	@Override
	public boolean isValid(UsuarioNewDTO objDto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getTipo().equals(TipoUsuario.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}

		if (objDto.getTipo().equals(TipoUsuario.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}

		Usuario aux = repo.findByEmail(objDto.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email", "Email já existente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
