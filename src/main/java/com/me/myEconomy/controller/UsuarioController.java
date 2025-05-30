package com.me.myEconomy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.me.myEconomy.exception.MeException;
import com.me.myEconomy.model.entity.Usuario;
import com.me.myEconomy.service.UsuarioService;

import auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.annotation.MultipartConfig;

@RestController
@RequestMapping(path = "/usuarios")
@CrossOrigin(origins = { "http://localhost:3000" }, maxAge = 3600)
@MultipartConfig(fileSizeThreshold = 10485760)
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AuthService authService;

	@Operation(summary = "Buscar usuário por ID", description = "Retorna os dados de um usuário com base no ID informado.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
	@GetMapping(path = "/{idUsuario}")
	public ResponseEntity<Usuario> pesquisarPorId(@PathVariable Long idUsuario) throws MeException {
		Usuario usuario = usuarioService.pesquisarPorId(idUsuario);
		return ResponseEntity.ok((usuario));
	}

	@Operation(summary = "Obter usuário autenticado", description = "Retorna os dados do usuário atualmente autenticado.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Dados do usuário autenticado retornados com sucesso"),
			@ApiResponse(responseCode = "401", description = "Usuário não autenticado") })
	@GetMapping("/usuario-autenticado")
	public ResponseEntity<Usuario> buscarUsuarioAutenticado() throws MeException {
		Usuario usuario = authService.getUsuarioAutenticado();
		return ResponseEntity.ok((usuario));
	}

}