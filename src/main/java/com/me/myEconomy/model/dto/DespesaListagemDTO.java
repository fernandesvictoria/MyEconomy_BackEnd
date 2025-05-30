package com.me.myEconomy.model.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DespesaListagemDTO {

    private String descricao;
    private String valor;
    private LocalDate mes;
}