package com.me.myEconomy.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.me.myEconomy.model.dto.LimiteListagemDTO;
import com.me.myEconomy.model.entity.Limite;

@Repository
public interface LimiteRepository extends JpaRepository<Limite, String>, JpaSpecificationExecutor<Limite> {

	@Query("SELECT new com.me.myEconomy.model.dto.LimiteListagemDTO(s.valor, s.mes) FROM Limite s WHERE s.usuario.idUsuario = :usuarioId")
	List<LimiteListagemDTO> findAllByUsuarioId(@Param("usuarioId") Long usuarioId);

}
