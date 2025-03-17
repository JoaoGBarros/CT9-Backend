package org.br.ct9backend.curso.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "curso", uniqueConstraints = {@UniqueConstraint(columnNames = {"nome"})})
public class Curso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String codigo;

    @NotNull
    private Integer cargaHorariaTotal;

    @NotNull
    private Integer cargaHorariaObrigatoria;

    @NotNull
    private Integer cargaHorariaOptativa;

    @NotNull
    private Integer cargaHorariaExtensao;

    @NotNull
    private Integer cargaHorariaComplementar;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "curso_ppc",
            joinColumns = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "ppc_id")
    )
    private List<PPC> ppc;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Semestre> semestres;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Coordenacao> coordenacao;

}