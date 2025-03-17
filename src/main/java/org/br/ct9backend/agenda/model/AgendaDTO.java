package org.br.ct9backend.agenda.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AgendaDTO {
    private Long disciplinaId;
    private String nomeDisciplina;
    private String codigoDisciplina;
    private String turma;
    private String predio;
    private String sala;
    private LocalTime horaInicio;
    private LocalTime horaFim;
}
