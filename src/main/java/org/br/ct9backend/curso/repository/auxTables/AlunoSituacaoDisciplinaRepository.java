package org.br.ct9backend.curso.repository.auxTables;

import org.br.ct9backend.curso.model.auxTable.AlunoSituacaoDisciplina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoSituacaoDisciplinaRepository extends JpaRepository<AlunoSituacaoDisciplina, Long> {
    void deleteBySituacaoDisciplinaId(Long alunoId);
}
