package com.adoise.library.model;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mail {
		
	@Size(min=3, message = "El nombre debe contener al menos 3 caracteres")
	String name;
	
	@Email(message = "Incluya un email valido")
	@NotEmpty(message = "El email no puede estar vacio.")
	String email;
	
	String emailCc;
	
	@Size(min=30, message = "El mensaje debe contener al menos 30 caracteres")
	String message;
	
	Date createAt;
	
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}
}
