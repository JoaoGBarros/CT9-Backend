package org.br.ct9backend.aluno.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PorcentagemCursoCompletoDTO {

    Integer horasCompletadas;
    Integer horasTotal;
    Integer porcentagem;
}
