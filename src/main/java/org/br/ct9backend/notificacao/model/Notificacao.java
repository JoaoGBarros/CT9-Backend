package org.br.ct9backend.notificacao.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Notificacao {

    private String topic;
    private String titulo;
    private String mensagem;

}
