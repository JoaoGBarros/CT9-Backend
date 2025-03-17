package org.br.ct9backend.curso.model.auxTable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.br.ct9backend.aluno.model.Aluno;
import org.br.ct9backend.curso.model.Coordenacao;
import org.br.ct9backend.curso.model.Curso;
import org.br.ct9backend.curso.model.SituacaoDisciplina;

@Entity
@Table(name = "aluno_situacoes_disciplina")
@Getter
@Setter
public class AlunoSituacaoDisciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "situacoes_disciplina_id", nullable = false)
    private SituacaoDisciplina situacaoDisciplina;
}
