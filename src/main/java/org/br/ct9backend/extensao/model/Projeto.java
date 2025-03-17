package org.br.ct9backend.extensao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Table
@Entity
public class Projeto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String descricao;

    @ElementCollection
    @NotNull
    @CollectionTable(name = "projeto_professores", joinColumns = @JoinColumn(name = "projeto_id"))
    private List<String> professoresResponsaveis;
}
