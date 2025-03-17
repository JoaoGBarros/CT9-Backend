package org.br.ct9backend.curso.repository.auxTables;

import org.br.ct9backend.curso.model.auxTable.CursoCoordenacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoCoordenacaoRepository extends JpaRepository<CursoCoordenacao, Long> {
    void deleteByCoordenacaoId(Long coordenacaoId);
}