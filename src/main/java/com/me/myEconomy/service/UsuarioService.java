package com.me.myEconomy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.me.myEconomy.exception.MeException;
import com.me.myEconomy.model.entity.Usuario;
import com.me.myEconomy.model.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

		return (UserDetails) usuario;
	}

	public void cadastrar(Usuario usuario) throws MeException {
		if (usuarioRepository.existsByEmailIgnoreCase(usuario.getEmail())) {
			throw new MeException("O e-mail informado já está cadastrado.", HttpStatus.BAD_REQUEST);
		}
		usuarioRepository.save(usuario);
	}

	public Usuario pesquisarPorId(Long id) throws MeException {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new MeException("Usuário não encontrado.", HttpStatus.NOT_FOUND));
	}

}