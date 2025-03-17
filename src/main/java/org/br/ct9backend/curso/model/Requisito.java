package org.br.ct9backend.curso.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.br.ct9backend.curso.model.enums.TipoRequisito;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Requisito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoRequisito tipo;

    private String codigo;

    private Integer periodoVencido;

    private Integer cargaHorariaVencida;

    private Integer creditos;
}