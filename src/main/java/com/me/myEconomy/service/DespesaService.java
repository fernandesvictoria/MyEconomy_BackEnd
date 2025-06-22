package com.me.myEconomy.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.me.myEconomy.exception.MeException;
import com.me.myEconomy.model.dto.DespesaDTO;
import com.me.myEconomy.model.dto.DespesaListagemDTO;
import com.me.myEconomy.model.entity.Despesa;
import com.me.myEconomy.model.entity.Usuario;
import com.me.myEconomy.model.repository.DespesaRepository;
import com.me.myEconomy.model.repository.UsuarioRepository;

@Service
public class DespesaService {

	@Autowired
	private DespesaRepository despesaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public HttpStatus cadastrarDespesa(DespesaDTO dto, Usuario usuario) throws MeException {

		LocalDate mesAtual = dto.getData();

		if (mesAtual.isBefore(LocalDate.now().withDayOfMonth(1))) {
			throw new MeException("Não é possível cadastrar depesas para meses passados.", HttpStatus.BAD_REQUEST);
		}
		Despesa despesa = new Despesa();
		despesa.setDescricao(dto.getDescricao());
		despesa.setValor(arredondarDuasCasas(dto.getValor()));
		despesa.setData(dto.getData());
		despesa.setUsuario(usuario);
		despesaRepository.save(despesa);

		return HttpStatus.CREATED;
	}

	public HttpStatus editarDespesa(String idDespesa, DespesaDTO dto, Usuario usuario) throws MeException {
		Despesa despesaExistente = despesaRepository.findById(idDespesa)
				.orElseThrow(() -> new MeException("Despesa não encontrada", HttpStatus.NOT_FOUND));

		if (!despesaExistente.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
			throw new MeException("Você não tem permissão para editar esta despesa", HttpStatus.FORBIDDEN);
		}

		LocalDate novaData = dto.getData();
		if (novaData.isBefore(LocalDate.now().withDayOfMonth(1))) {
			throw new MeException("Não é possível cadastrar despesas para meses passados.", HttpStatus.BAD_REQUEST);
		}

		despesaExistente.setDescricao(dto.getDescricao());
		despesaExistente.setValor(arredondarDuasCasas(dto.getValor()));
		despesaExistente.setData(novaData);

		despesaRepository.save(despesaExistente);

		return HttpStatus.OK;
	}

	public void deletarDespesa(String itemId) throws MeException {
		if (!despesaRepository.existsById(itemId)) {
			throw new MeException("Despesa não encontrada", HttpStatus.NOT_FOUND);
		}
		despesaRepository.deleteById(itemId);
	}

	public List<DespesaListagemDTO> buscarDespesasDoUsuario(Long usuarioId) throws MeException {
		Usuario usuario = usuarioRepository.findById(usuarioId)
		        .orElseThrow(() -> new MeException("Usuário não encontrado",HttpStatus.NOT_FOUND));

		    List<Despesa> despesas = despesaRepository.findAllByUsuario(usuario);
	    return despesas.stream()
	        .map(despesa -> new DespesaListagemDTO(
	        		despesa.getIdDespesa(),
	        		despesa.getDescricao(),
	        		despesa.getValor(),
	        		despesa.getData()
	        ))
	        .collect(Collectors.toList());
	}

	public Double somarDespesasDoMes(LocalDate data, Usuario usuario) {
		Double total = despesaRepository.somarDespesasDoMes(usuario.getIdUsuario(), data);

		return total != null ? arredondarDuasCasas(total) : 0.0;
	}

	private Double arredondarDuasCasas(Double valor) {
		return BigDecimal.valueOf(valor).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

}
