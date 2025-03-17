package org.br.ct9backend.curso.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table
@Entity
@Getter
@Setter
public class Semestre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String semestre;

    @NotNull
    private Date dataInicio;

    @NotNull
    private Date dataFim;

    @OneToMany
    private List<DisciplinaOfertada> disciplinasOfertadas;
}
