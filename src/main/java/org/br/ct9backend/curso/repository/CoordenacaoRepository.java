package org.br.ct9backend.curso.repository;

import org.br.ct9backend.curso.model.Coordenacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordenacaoRepository extends JpaRepository<Coordenacao, Long> {
}
