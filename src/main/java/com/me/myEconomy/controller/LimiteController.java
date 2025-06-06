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
import com.me.myEconomy.model.dto.LimiteDTO;
import com.me.myEconomy.model.dto.LimiteListagemDTO;
import com.me.myEconomy.model.entity.Usuario;
import com.me.myEconomy.service.LimiteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/limite")
public class LimiteController {

	@Autowired
	private LimiteService limiteService;

	@Autowired
	private AuthService authService;

	@PostMapping
	public ResponseEntity<Void> cadastrarLimite(@Valid @RequestBody LimiteDTO dto) throws MeException {

		Usuario usuario = authService.getUsuarioAutenticado();

		limiteService.cadastrarLimite(dto, usuario);

		return ResponseEntity.created(null).build();
	}

	@PutMapping("/{idLimite}")
	public ResponseEntity<Void> editarLimite(@PathVariable String idLimite, @Valid @RequestBody LimiteDTO dto)
			throws MeException {

		Usuario usuario = authService.getUsuarioAutenticado();
		limiteService.editarLimite(idLimite, dto, usuario);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping(path = "/{idLimite}")
	public void deletarItem(@PathVariable String idLimite) throws MeException {
		Usuario usuario = authService.getUsuarioAutenticado();

		limiteService.deletarLimite(idLimite);
	}

	@GetMapping("/limites")
	public List<LimiteListagemDTO> buscarTodasDespesas() throws MeException {
		Usuario usuario = authService.getUsuarioAutenticado();
		return limiteService.buscarLimiteDoUsuario(usuario.getIdUsuario());
	}
}