package org.br.ct9backend.aluno.model.dto;


import lombok.Getter;
import lombok.Setter;
import org.br.ct9backend.curso.model.SituacaoDisciplina;
import org.br.ct9backend.curso.model.enums.Situacao;

@Getter
@Setter
public class AtualizaSituacaoRequestDTO {

    private Situacao situacao;
    private Long disciplinaId;
}
