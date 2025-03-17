package org.br.ct9backend.curso.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table
@Getter
@Setter
public class Alocacao implements Serializable {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private DayOfWeek diaSemana;

    private LocalTime horaInicio;

    private LocalTime horaFim;

    private String predio;

    private String sala;

}
