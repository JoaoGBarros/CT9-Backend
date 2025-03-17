package org.br.ct9backend.curso.repository;


import org.br.ct9backend.curso.model.Alocacao;
import org.br.ct9backend.curso.model.dto.AlocacaoResponseDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AlocacaoRepository extends JpaRepository<Alocacao, Long> {

    @Query("SELECT a FROM DisciplinaOfertada d " +
            "JOIN d.alocacoes a " +
            "WHERE d.id = :disciplinaOfertadaId")
    List<Alocacao> findByDisciplinaOfertadaId(@Param("disciplinaOfertadaId") Long disciplinaOfertadaId);

//    @Query("SELECT a FROM Alocacao a " +
//            "WHERE a.predio = :predio " +
//            "AND a.sala = :sala " +
//            "AND a.diaSemana = :diaSemana " +
//            "AND a.horaFim > :horarioAtual")
//    List<Alocacao> findByDiaSemanaAndPredioAndSalasAndHorario(@Param("predio") String predio, @Param("sala") String sala, @Param("diaSemana") DayOfWeek diaSemana, @Param("horarioAtual") LocalTime horarioAtual);

    @Query("SELECT new org.br.ct9backend.curso.model.dto.AlocacaoResponseDTO(a.horaInicio, a.horaFim, d.disciplina.codigo, d.disciplina.nome, d.turma) " +
            "FROM Alocacao a " +
            "JOIN DisciplinaOfertada d ON a MEMBER OF d.alocacoes " +
            "WHERE a.diaSemana = :diaSemana AND a.predio = :predio AND a.sala = :sala AND a.horaFim > :horaAtual")
    List<AlocacaoResponseDTO> findAlocacoesComDisciplina(DayOfWeek diaSemana, String predio, String sala, LocalTime horaAtual);
}
