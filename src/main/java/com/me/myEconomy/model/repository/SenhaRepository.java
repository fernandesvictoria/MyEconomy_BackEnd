package com.me.myEconomy.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.me.myEconomy.model.dto.SenhaListagemDTO;
import com.me.myEconomy.model.entity.Senha;

@Repository
public interface SenhaRepository extends JpaRepository<Senha, String>, JpaSpecificationExecutor<Senha> {

	@Query("SELECT new com.gs.geradorSenha.model.dto.SenhaListagemDTO(s.nome, s.senha) FROM Senha s WHERE s.usuario.id = :usuarioId")
	List<SenhaListagemDTO> findAllByUsuarioId(@Param("usuarioId") Long usuarioId);

	Optional<Senha> findByNome(String nome);
}
