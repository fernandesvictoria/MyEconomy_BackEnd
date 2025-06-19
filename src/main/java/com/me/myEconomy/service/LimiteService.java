package com.me.myEconomy.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.me.myEconomy.exception.MeException;
import com.me.myEconomy.model.dto.LimiteDTO;
import com.me.myEconomy.model.dto.LimiteListagemDTO;
import com.me.myEconomy.model.entity.Limite;
import com.me.myEconomy.model.entity.Usuario;
import com.me.myEconomy.model.repository.DespesaRepository;
import com.me.myEconomy.model.repository.LimiteRepository;
import com.me.myEconomy.model.repository.UsuarioRepository;

@Service
public class LimiteService {

	@Autowired
	private LimiteRepository limiteRepository;
	@Autowired
	private DespesaRepository despesaRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;


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
		limite.setValor(arredondarDuasCasas(dto.getValor()));
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

		limiteExistente.setValor(arredondarDuasCasas(dto.getValor()));
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

	public List<LimiteListagemDTO> buscarLimiteDoUsuario(Long usuarioId) throws MeException {
		Usuario usuario = usuarioRepository.findById(usuarioId)
		        .orElseThrow(() -> new MeException("Usuário não encontrado",HttpStatus.NOT_FOUND));

		    List<Limite> limites = limiteRepository.findAllByUsuario(usuario);
	    return limites.stream()
	        .map(limite -> new LimiteListagemDTO(
	            limite.getIdLimite(),
	            limite.getValor(),
	            limite.getData()
	        ))
	        .collect(Collectors.toList());
	}

	public Double calcularSaldoDoMes(Usuario usuario, LocalDate data) throws MeException {
		Optional<Limite> limiteOpt = limiteRepository.findByUsuarioAndMesAndAno(usuario, data);

		if (limiteOpt.isEmpty()) {
			throw new MeException("Nenhum limite cadastrado para este mês.", HttpStatus.NOT_FOUND);
		}

		Double limite = limiteOpt.get().getValor();

		Double totalDespesas = despesaRepository.somarDespesasDoMes(usuario.getIdUsuario(), data);
		if (totalDespesas == null) {
			totalDespesas = 0.0;
		}

		Double saldo = limite - totalDespesas;

		return arredondarDuasCasas(saldo);
	}

	private Double arredondarDuasCasas(Double valor) {
		return BigDecimal.valueOf(valor).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

}
