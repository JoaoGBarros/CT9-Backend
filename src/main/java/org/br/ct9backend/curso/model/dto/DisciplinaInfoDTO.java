package org.br.ct9backend.curso.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DisciplinaInfoDTO {
    private Long id;
    private String nome;
    private String codigo;
    private String requisitos;
    private String departamento;
    private Double notaDificuldade;
    private Double notaTempoGasto;
    private String situacao;
    private String periodo;
    private Integer cargaHoraria;
    private String ementa;
    private Boolean avaliou;
}