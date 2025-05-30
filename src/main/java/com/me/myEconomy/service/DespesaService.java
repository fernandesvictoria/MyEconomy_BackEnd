package com.me.myEconomy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.me.myEconomy.exception.MeException;
import com.me.myEconomy.model.dto.DespesaDTO;
import com.me.myEconomy.model.dto.DespesaListagemDTO;
import com.me.myEconomy.model.entity.Despesa;
import com.me.myEconomy.model.entity.Usuario;
import com.me.myEconomy.model.repository.DespesaRepository;

@Service
public class DespesaService {

	@Autowired
	private DespesaRepository despesaRepository;

	public Despesa criarSenha(Despesa despesa) throws MeException {
		return despesaRepository.save(despesa);
	}

	 public HttpStatus cadastrarDespesa(DespesaDTO dto, Usuario usuario) throws MeException{

	        if(despesaRepository.findByDescricao(dto.getDescricao()).isPresent()) {
	            throw new MeException("Nome do item já cadastrado", HttpStatus.CONFLICT);
	        }
	        Despesa despesa = new Despesa();
	        despesa.setDescricao(dto.getDescricao());
	        despesa.setValor(dto.getValor());
	        despesa.setMes(dto.getMes());
	        despesa.setUsuario(usuario);
	        despesaRepository.save(despesa);

	        return HttpStatus.CREATED;
	    }
	
	   public void deletarDespesa(String itemId) throws MeException{
	        if (!despesaRepository.existsById(itemId)) {
	            throw new MeException("Despesa nao encontrada",HttpStatus.NOT_FOUND);
	        }
	        despesaRepository.deleteById(itemId);
	    }
	   
	   public List<DespesaListagemDTO> buscarDespesasDoUsuario(Long idUsuario) {
		   return despesaRepository.findAllByUsuarioId(idUsuario);		}
	

}
