package org.br.ct9backend.curso.model.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.br.ct9backend.curso.model.Alocacao;
import org.br.ct9backend.curso.model.Disciplina;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
public class DisciplinaOfertadaDTO {

    private Long id;

    private String codigoDisciplina;

    private String turma;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Alocacao> alocacoes;
}
