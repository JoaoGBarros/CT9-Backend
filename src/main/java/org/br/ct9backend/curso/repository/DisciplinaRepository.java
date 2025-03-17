package org.br.ct9backend.curso.repository;

import org.br.ct9backend.curso.model.Disciplina;
import org.br.ct9backend.curso.model.enums.TipoRequisito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    @Query("SELECT d FROM Curso c " +
            "JOIN c.ppc p " +
            "JOIN p.disciplinas d " +
            "WHERE (c.id = :codigoCurso AND d.codigo = :codigoDisciplina) " +
            "OR (c.id = :codigoCurso AND :codigoDisciplina IN elements(d.equivalencias))")
    Optional<Disciplina> findByCodigoDisciplinaAndCursoCodigo(
            @Param("codigoDisciplina") String codigoDisciplina,
            @Param("codigoCurso") Long codigoCurso
    );

    @Query("SELECT d FROM Disciplina d WHERE d.codigo = :codigoDisciplina")
    Optional<Disciplina> findByCodigoDisciplina(String codigoDisciplina);

    @Query("SELECT d FROM Disciplina d JOIN d.requisitos r WHERE r.tipo = 'Disciplina' AND r.codigo = :codigoDisciplina")
    List<Disciplina> findDisciplinasDependentes(@Param("codigoDisciplina") String codigoDisciplina);
}
