package com.me.myEconomy.model.entity;

import java.time.LocalDate;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Despesa {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@UuidGenerator
	private String  idDespesa;

	private Double valor;
	
	@NotBlank(message = "Nome da senha é obrigatório")
	private String descricao;
	
	private LocalDate data;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	@JsonBackReference("usuario-despesa")
	private Usuario usuario;
}
