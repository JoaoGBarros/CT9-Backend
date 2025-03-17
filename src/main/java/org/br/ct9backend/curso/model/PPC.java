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
@Table (uniqueConstraints = {@UniqueConstraint(columnNames = {"edicao", "ativo"})})
public class PPC implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String edicao;

    @NotNull
    private Boolean ativo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull
    @JoinTable(
            name = "ppc_disciplinas",
            joinColumns = @JoinColumn(name = "ppc_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplina_id")
    )
    private List<Disciplina> disciplinas;


}
