package org.br.ct9backend.curso.service;

import jakarta.transaction.Transactional;
import org.br.ct9backend.curso.model.Alocacao;
import org.br.ct9backend.curso.model.Disciplina;
import org.br.ct9backend.curso.model.DisciplinaOfertada;
import org.br.ct9backend.curso.model.dto.AvaliarDisciplinaRequest;
import org.br.ct9backend.curso.model.dto.DisciplinaOfertadaDTO;

import java.util.List;

public interface DisciplinaService {

    Disciplina getDisciplina(Long id);

    Disciplina editarDisciplina(Long id, Disciplina disciplina);

    List<DisciplinaOfertada> getDisciplinasOfertadas(Long semestreId);

    List<DisciplinaOfertada> importDisciplinasOfertadas(Long semestreId, Long cursoId, List<DisciplinaOfertadaDTO> disciplinas);

    DisciplinaOfertada saveDisciplinasOfertadas(Long semestreId, Long cursoId, DisciplinaOfertadaDTO disciplinas);

    DisciplinaOfertada getDisciplinaOfertada(Long disciplinaId);

    List<Alocacao> getAlocacao(Long disciplinaId);

    DisciplinaOfertada updateDisciplinaOfertada(DisciplinaOfertadaDTO disciplina, Long disciplinaId);

    Disciplina saveDisciplina(Long ppcId, Disciplina disciplina);

    void deleteAlocacao(Long alocacaoId);

    @Transactional
    void deleteDisciplina(Long id);

    void avaliarDisciplina(AvaliarDisciplinaRequest avaliacaoRequest, String userId);
}
