package org.br.ct9backend.curso.service;

import org.br.ct9backend.curso.model.Alocacao;
import org.br.ct9backend.curso.model.dto.AlocacaoResponseDTO;

import java.util.List;

public interface AlocacaoService {

    List<Alocacao> getAlocacoesFromSemester(Long semestreId);

    Alocacao getAlocacao(Long alocacaoId);

    Alocacao saveAlocacao(Long disciplinaId, Alocacao alocacao);

    Alocacao updateAlocacao(Long alocacaoId, Alocacao alocacao);

    void deleteAlocacao(Long alocacaoId);

    List<AlocacaoResponseDTO> getAlocaoSalaHoje(String predio, String sala);
}
