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
import lombok.Data;

@Entity
@Data
public class Limite {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@UuidGenerator
	private String idLimite;

	private String valor;

	private LocalDate mes;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	@JsonBackReference("usuario-limite")
	private Usuario usuario;
}
