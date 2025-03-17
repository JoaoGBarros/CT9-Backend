package org.br.ct9backend.curso.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Embeddable
@Getter
@Setter
public class Horario {

    private DayOfWeek diaSemana;

    private LocalTime horaInicio;

    private LocalTime horaFim;
}
