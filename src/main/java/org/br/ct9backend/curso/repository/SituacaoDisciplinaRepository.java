package org.br.ct9backend.curso.repository;


import org.br.ct9backend.aluno.model.Aluno;
import org.br.ct9backend.curso.model.SituacaoDisciplina;
import org.br.ct9backend.curso.model.enums.Situacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SituacaoDisciplinaRepository extends JpaRepository<SituacaoDisciplina, Long> {
    void deleteByDisciplinaId(Long disciplinaId);
    List<SituacaoDisciplina> findByDisciplinaId(Long disciplinaId);
    Optional<SituacaoDisciplina> findByDisciplinaIdAndAlunoId(Long disciplinaId, Long alunoId);

    boolean existsByAlunoAndDisciplinaCodigoAndSituacao(Aluno aluno, String codigoDisciplina, Situacao situacao);

    @Query("SELECT SUM(sd.disciplina.creditos) FROM SituacaoDisciplina sd WHERE sd.aluno.id = :alunoId AND sd.situacao = :situacao")
    Integer sumCreditosByAlunoAndSituacao(Long id, Situacao situacao);
}
