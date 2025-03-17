package org.br.ct9backend.extensao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.br.ct9backend.extensao.model.enums.TipoExtensao;

import java.io.Serializable;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Extensao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String descricao;

    @NotNull
    private String imagemUrl;

    @NotNull
    private String nome;

    private String instagram;

    private String site;

    @ElementCollection
    @NotNull
    @CollectionTable(name = "extensao_professores", joinColumns = @JoinColumn(name = "extensao_id"))
    private List<String> professoresResponsaveis;

    @NotNull
    private TipoExtensao tipo;



}
