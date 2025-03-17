package org.br.ct9backend.curso.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.br.ct9backend.curso.model.enums.TipoCoordenador;


@Entity
@Table
@Getter
@Setter
public class Coordenacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoCoordenador tipoCoordenador;

    @NotNull
    private String nome;

    @NotNull
    private String email;
}
