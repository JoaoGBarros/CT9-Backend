package org.br.ct9backend.curso.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.br.ct9backend.curso.model.enums.Obrigatoriedade;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Disciplina implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String periodo;

    @NotNull
    private String codigo;

    @NotNull
    private Integer cargaHoraria;

    @NotNull
    private Integer creditos;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Obrigatoriedade obrigatoriedade;

    @OneToMany (cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "disciplina_requisito",
            joinColumns = @JoinColumn(name = "disciplina_id"),
            inverseJoinColumns = @JoinColumn(name = "requisito_id")
    )
    private List<Requisito> requisitos;

    @Column(columnDefinition = "TEXT")
    private String ementa;

    @ElementCollection
    @CollectionTable(name = "disciplina_equivalencia", joinColumns = @JoinColumn(name = "disciplina_id"))
    private List<String> equivalencias;

    @ElementCollection
    @CollectionTable(name="nota_tempo_gasto", joinColumns = @JoinColumn(name = "disciplina_id"))
    private List<Double> notaDificuldade;

    @ElementCollection
    @CollectionTable(name="nota_tempo_gasto", joinColumns = @JoinColumn(name = "disciplina_id"))
    private List<Double> notaTempoGasto;

    public double getMediaNotaDificuldade() {
        return notaDificuldade.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    public double getMediaNotaTempoGasto() {
        return notaTempoGasto.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

}