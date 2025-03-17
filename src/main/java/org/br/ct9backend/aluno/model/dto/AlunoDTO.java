package org.br.ct9backend.aluno.model.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.br.ct9backend.agenda.model.Agenda;
import org.br.ct9backend.curso.model.Curso;
import org.br.ct9backend.curso.model.SituacaoDisciplina;
import org.br.ct9backend.security.auth.model.User;

import java.util.List;


@Getter
@Setter
public class AlunoDTO extends User {

    private Long curso;

}