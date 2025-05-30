package com.me.myEconomy.model.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LimiteListagemDTO {

	private String valor;
	private LocalDate mes;
}