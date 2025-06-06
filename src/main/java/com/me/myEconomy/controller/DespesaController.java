package com.me.myEconomy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.me.myEconomy.auth.AuthService;
import com.me.myEconomy.exception.MeException;
import com.me.myEconomy.model.dto.DespesaDTO;
import com.me.myEconomy.model.dto.DespesaListagemDTO;
import com.me.myEconomy.model.entity.Usuario;
import com.me.myEconomy.service.DespesaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/despesa")
public class DespesaController {

	@Autowired
	private DespesaService despesaService;

	@Autowired
	private AuthService authService;

	@PostMapping
	public ResponseEntity<Void> cadastrarDespesa(@Valid @RequestBody DespesaDTO dto) throws MeException {

		Usuario usuario = authService.getUsuarioAutenticado();

		despesaService.cadastrarDespesa(dto, usuario);

		return ResponseEntity.created(null).build();
	}

	@PutMapping("/{idDespesa}")
	public ResponseEntity<Void> editarDespesa(@PathVariable String idDespesa, @Valid @RequestBody DespesaDTO dto)
			throws MeException {

		Usuario usuario = authService.getUsuarioAutenticado();
		despesaService.editarDespesa(idDespesa, dto, usuario);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping(path = "/{idDespesa}")
	public void deletarItem(@PathVariable String idDespesa) throws MeException {
		Usuario usuario = authService.getUsuarioAutenticado();

		despesaService.deletarDespesa(idDespesa);
	}

	@GetMapping("/despesas")
	public List<DespesaListagemDTO> buscarTodasDespesas() throws MeException {
		Usuario usuario = authService.getUsuarioAutenticado();
		return despesaService.buscarDespesasDoUsuario(usuario.getIdUsuario());
	}
}