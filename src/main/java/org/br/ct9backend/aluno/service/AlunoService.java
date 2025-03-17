package org.br.ct9backend.aluno.service;

import org.br.ct9backend.aluno.model.Aluno;
import org.br.ct9backend.aluno.model.dto.AlunoDTO;
import org.br.ct9backend.aluno.model.dto.AtualizaSituacaoRequestDTO;
import org.br.ct9backend.aluno.model.dto.PorcentagemCursoCompletoDTO;
import org.br.ct9backend.curso.model.dto.DisciplinaInfoDTO;

import java.util.HashMap;
import java.util.List;

public interface AlunoService {
    AlunoDTO criarAluno(AlunoDTO aluno);

    AlunoDTO buscarAluno(String firebaseUid);

    HashMap<String, List<DisciplinaInfoDTO>> getDisciplinasInfo(String firebaseUid);

    PorcentagemCursoCompletoDTO getPorcentagem(String firebaseUid);

    String updateSituacao(String firebaseUid, List<AtualizaSituacaoRequestDTO> aluno);
}
