package com.me.myEconomy.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.me.myEconomy.auth.AuthService;
import com.me.myEconomy.exception.MeException;
import com.me.myEconomy.model.dto.SenhaDTO;
import com.me.myEconomy.model.dto.SenhaListagemDTO;
import com.me.myEconomy.model.entity.Usuario;
import com.me.myEconomy.service.SenhaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/senha")
public class SenhaController {

    @Autowired
    private SenhaService senhaService;

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<Void> cadastrarItem(@Valid @RequestBody SenhaDTO dto) throws MeException {

        Usuario usuario = authService.getUsuarioAutenticado();

        senhaService.cadastrarSenha(dto, usuario);

        return ResponseEntity.created(null).build();
    }

    @DeleteMapping(path = "/{idSenha}")
    public void deletarItem(@PathVariable String idSenha) throws MeException {
        Usuario usuario = authService.getUsuarioAutenticado();

        senhaService.deletarItem(idSenha);
    }

    @GetMapping("/senhas")
    public List<SenhaListagemDTO> buscarTodasSenhas() throws MeException {
        Usuario usuario = authService.getUsuarioAutenticado();
        return senhaService.buscarSenhasDoUsuario(usuario.getIdUsuario());
    }
}