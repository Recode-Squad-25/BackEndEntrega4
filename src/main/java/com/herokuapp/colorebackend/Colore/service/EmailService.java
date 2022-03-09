package com.herokuapp.colorebackend.Colore.service;

import org.springframework.mail.SimpleMailMessage;

import br.com.colore.models.Usuario;

public interface EmailService {
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(Usuario usuario, String newPass);
}
