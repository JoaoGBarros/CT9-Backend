package org.br.ct9backend.ru.service;

import org.br.ct9backend.ru.model.Cardapio;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CardapioService {
    boolean verificarEAtualizarCardapio() throws IOException;

    Map<String, Map<String, List<String>>> getCardapioDoDia();
}
