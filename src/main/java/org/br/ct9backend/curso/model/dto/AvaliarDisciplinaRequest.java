package org.br.ct9backend.curso.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AvaliarDisciplinaRequest {
    private Long disciplinaId;
    private Double notaTempo;
    private Double notaDificuldade;
}
