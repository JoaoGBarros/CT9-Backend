package org.br.ct9backend.curso.model.auxTable;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.br.ct9backend.curso.model.Disciplina;
import org.br.ct9backend.curso.model.PPC;

@Entity
@Table(name = "ppc_disciplinas")
@Getter
@Setter
public class PPCDisciplinas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ppc_id", nullable = false)
    private PPC ppc;

    @ManyToOne
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;
}
