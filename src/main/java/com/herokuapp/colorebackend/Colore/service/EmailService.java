package com.herokuapp.colorebackend.Colore.service;

import org.springframework.mail.SimpleMailMessage;

import com.herokuapp.colorebackend.Colore.models.Usuario;

public interface EmailService {
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(Usuario usuario, String newPass);
}
