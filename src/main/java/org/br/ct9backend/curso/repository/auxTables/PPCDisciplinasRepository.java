package org.br.ct9backend.curso.repository.auxTables;

import org.br.ct9backend.curso.model.auxTable.PPCDisciplinas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PPCDisciplinasRepository extends JpaRepository<PPCDisciplinas, Long> {
    void deleteByDisciplinaId(Long disciplinaId);
}
