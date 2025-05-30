package com.me.myEconomy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.me.myEconomy.exception.MeException;
import com.me.myEconomy.model.dto.LimiteDTO;
import com.me.myEconomy.model.dto.LimiteListagemDTO;
import com.me.myEconomy.model.entity.Limite;
import com.me.myEconomy.model.entity.Usuario;
import com.me.myEconomy.model.repository.LimiteRepository;

@Service
public class LimiteService {

	@Autowired
	private LimiteRepository limiteRepository;

	public Limite criarLimite(Limite limite) throws MeException {
		return limiteRepository.save(limite);
	}

	public HttpStatus cadastrarLimite(LimiteDTO dto, Usuario usuario) throws MeException {

		
		Limite limite = new Limite();
		limite.setValor(dto.getValor());
		limite.setMes(dto.getMes());
		limite.setUsuario(usuario);
		limiteRepository.save(limite);

		return HttpStatus.CREATED;
	}

	public void deletarLimite(String itemId) throws MeException {
		if (!limiteRepository.existsById(itemId)) {
			throw new MeException("Limite nao encontrado", HttpStatus.NOT_FOUND);
		}
		limiteRepository.deleteById(itemId);
	}

	public List<LimiteListagemDTO> buscarLimiteDoUsuario(Long idUsuario) {
		return limiteRepository.findAllByUsuarioId(idUsuario);
	}

}
