package com.me.myEconomy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SenhaDTO {

	private String nome;

	private String senha;

	private String idUsuario;
}