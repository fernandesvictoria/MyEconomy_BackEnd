package com.me.myEconomy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.me.myEconomy.exception.MeException;
import com.me.myEconomy.model.dto.SenhaDTO;
import com.me.myEconomy.model.dto.SenhaListagemDTO;
import com.me.myEconomy.model.entity.Senha;
import com.me.myEconomy.model.entity.Usuario;
import com.me.myEconomy.model.repository.SenhaRepository;

@Service
public class SenhaService {

	@Autowired
	private SenhaRepository senhaRepository;

	public Senha criarSenha(Senha senha) throws MeException {
		return senhaRepository.save(senha);
	}

	 public HttpStatus cadastrarSenha(SenhaDTO dto, Usuario usuario) throws MeException{

	        if(senhaRepository.findByNome(dto.getNome()).isPresent()) {
	            throw new MeException("Nome do item já cadastrado", HttpStatus.CONFLICT);
	        }
	        Senha senha = new Senha();
	        senha.setNome(dto.getNome());
	        senha.setSenha(dto.getSenha());
	        senha.setUsuario(usuario);
	        senhaRepository.save(senha);

	        return HttpStatus.CREATED;
	    }
	
	   public void deletarItem(String itemId) throws MeException{
	        if (!senhaRepository.existsById(itemId)) {
	            throw new MeException("Item nao encontrado",HttpStatus.NOT_FOUND);
	        }
	        senhaRepository.deleteById(itemId);
	    }
	   
	   public List<SenhaListagemDTO> buscarSenhasDoUsuario(Long idUsuario) {
		   return senhaRepository.findAllByUsuarioId(idUsuario);		}
	

}
