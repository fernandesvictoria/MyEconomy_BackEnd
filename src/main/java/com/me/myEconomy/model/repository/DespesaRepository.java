package com.me.myEconomy.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.me.myEconomy.model.dto.DespesaListagemDTO;
import com.me.myEconomy.model.entity.Despesa;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, String>, JpaSpecificationExecutor<Despesa> {

	@Query("SELECT new com.me.myEconomy.model.dto.DespesaListagemDTO(s.descricao, s.valor,s.mes) FROM Despesa s WHERE s.usuario.id = :usuarioId")
	List<DespesaListagemDTO> findAllByUsuarioId(@Param("usuarioId") Long usuarioId);
	
	

	Optional<Despesa> findByDescricao(String descricao);
}
