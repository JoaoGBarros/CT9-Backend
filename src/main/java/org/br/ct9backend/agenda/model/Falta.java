package org.br.ct9backend.agenda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.br.ct9backend.curso.model.Disciplina;
import org.br.ct9backend.curso.model.SituacaoDisciplina;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
public class Falta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate dia;

}
