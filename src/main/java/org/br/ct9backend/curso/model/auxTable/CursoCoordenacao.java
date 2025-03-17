package org.br.ct9backend.curso.model.auxTable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.br.ct9backend.curso.model.Coordenacao;
import org.br.ct9backend.curso.model.Curso;

@Entity
@Table(name = "curso_coordenacao")
@Getter
@Setter
public class CursoCoordenacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @ManyToOne
    @JoinColumn(name = "coordenacao_id", nullable = false)
    private Coordenacao coordenacao;
}