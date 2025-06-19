package com.me.myEconomy.model.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.me.myEconomy.model.dto.DespesaListagemDTO;
import com.me.myEconomy.model.entity.Despesa;
import com.me.myEconomy.model.entity.Limite;
import com.me.myEconomy.model.entity.Usuario;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, String>, JpaSpecificationExecutor<Despesa> {

	List<Despesa> findAllByUsuario(Usuario usuario);
	
	@Query("SELECT l FROM Despesa l WHERE l.usuario = :usuario AND MONTH(l.data) = MONTH(:data) AND YEAR(l.data) = YEAR(:data)")
	Optional<Limite> findByUsuarioAndMesAndAno(@Param("usuario") Usuario usuario, @Param("data") LocalDate mes);
	
	@Query("SELECT SUM(d.valor) FROM Despesa d WHERE d.usuario.id = :usuarioId AND MONTH(d.data) = MONTH(:data) AND YEAR(d.data) = YEAR(:data)")
	Double somarDespesasDoMes(@Param("usuarioId") Long usuarioId, @Param("data") LocalDate data);

	Optional<Despesa> findByDescricao(String descricao);
}
