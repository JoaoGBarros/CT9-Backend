package org.br.ct9backend.curso.service;

import org.br.ct9backend.curso.model.PPC;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PPCService {

    PPC criarPPC(Long cursoID, PPC ppc);

    List<PPC> getPPCs(Long cursoID);

    PPC getPPC(Long cursoID, Long ppcID);

    void deletePPC(Long ppcId);
}
