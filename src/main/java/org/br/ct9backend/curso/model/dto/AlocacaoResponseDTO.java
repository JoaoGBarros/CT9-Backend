package org.br.ct9backend.curso.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.br.ct9backend.curso.model.Alocacao;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class AlocacaoResponseDTO {

    private LocalTime horaInicio;
    private LocalTime horaFim;
    private String disciplinaCodigo;
    private String disciplinaNome;
    private String turma;
}
