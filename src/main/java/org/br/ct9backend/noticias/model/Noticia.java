package org.br.ct9backend.noticias.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Table
public class Noticia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String titulo;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String texto;

    @NotNull
    private String imagemUrl;

    @NotNull
    private Date dataPublicacao;
}
