package org.br.ct9backend.agenda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table
@Getter
@Setter
public class Tarefa implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String tarefa;

    @NotNull
    private LocalDate data;

    private LocalTime horario;

    @NotNull
    private Boolean concluido;

}