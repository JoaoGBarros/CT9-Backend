package org.br.ct9backend.curso.model.auxTable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.br.ct9backend.curso.model.Curso;
import org.br.ct9backend.curso.model.Disciplina;
import org.br.ct9backend.curso.model.PPC;

@Entity
@Table(name = "curso_ppc")
@Getter
@Setter
public class CursoPPC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ppc_id", nullable = false)
    private PPC ppc;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
}
