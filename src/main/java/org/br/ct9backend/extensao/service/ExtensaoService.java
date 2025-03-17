package org.br.ct9backend.extensao.service;

import org.br.ct9backend.extensao.model.Extensao;

import java.util.List;

public interface ExtensaoService {
    List<Extensao> getTodasExtensao();

    Extensao saveExtensao(Extensao extensao);

    Extensao getExtensao(Long id);

    void deleteExtensao(Long id);

    Extensao updateExtensao(Long id, Extensao extensao);
}
