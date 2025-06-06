package com.me.myEconomy.service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.me.myEconomy.exception.MeException;
import com.me.myEconomy.model.dto.DespesaDTO;
import com.me.myEconomy.model.dto.LimiteDTO;
import com.me.myEconomy.model.dto.LimiteListagemDTO;
import com.me.myEconomy.model.entity.Despesa;
import com.me.myEconomy.model.entity.Limite;
import com.me.myEconomy.model.entity.Usuario;
import com.me.myEconomy.model.repository.LimiteRepository;

@Service
public class LimiteService {

	@Autowired
	private LimiteRepository limiteRepository;

	public HttpStatus cadastrarLimite(LimiteDTO dto, Usuario usuario) throws MeException {

		LocalDate mesAtual = dto.getData();

		// Verifica se já existe um limite para o usuário no MÊS/ANO
		Optional<Limite> limiteExistente = limiteRepository.findByUsuarioAndMesAndAno(usuario, mesAtual);

		if (limiteExistente.isPresent()) {
			throw new MeException("Já existe um limite cadastrado para "
					+ mesAtual.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + "/"
					+ mesAtual.getYear(), HttpStatus.BAD_REQUEST);
		}
		if (mesAtual.isBefore(LocalDate.now().withDayOfMonth(1))) {
			throw new MeException("Não é possível cadastrar limites para meses passados.", HttpStatus.BAD_REQUEST);
		}

		Limite limite = new Limite();
		limite.setValor(dto.getValor());
		limite.setData(dto.getData());
		limite.setUsuario(usuario);
		limiteRepository.save(limite);

		return HttpStatus.CREATED;
	}

	public HttpStatus editarLimite(String idLimite, LimiteDTO dto, Usuario usuario) throws MeException {
		Limite limiteExistente = limiteRepository.findById(idLimite)
				.orElseThrow(() -> new MeException("Despesa não encontrada", HttpStatus.NOT_FOUND));

		if (!limiteExistente.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
			throw new MeException("Você não tem permissão para editar este limite", HttpStatus.FORBIDDEN);
		}

		LocalDate novaData = dto.getData();
		if (novaData.isBefore(LocalDate.now().withDayOfMonth(1))) {
			throw new MeException("Não é possível cadastrar limites para meses passados.", HttpStatus.BAD_REQUEST);
		}

		limiteExistente.setValor(dto.getValor());
		limiteExistente.setData(novaData);

		limiteRepository.save(limiteExistente);

		return HttpStatus.OK;
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
