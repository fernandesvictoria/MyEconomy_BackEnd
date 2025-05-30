package com.me.myEconomy.model.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UsuarioDTO {

	private String nome;

	@Email
	private String email;

	private LocalDate dataNascimento;

	private String senha;

	private String confirmacaoSenha;

}