package org.br.ct9backend.curso.repository.auxTables;

import org.br.ct9backend.curso.model.auxTable.CursoPPC;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoPPCRepository extends JpaRepository<CursoPPC, Long> {
    void deleteByPpcId(Long ppcId);
}
