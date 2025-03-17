package org.br.ct9backend.ru.service.scrapper;

import java.io.IOException;
import java.util.Map;

public interface CardapioScraperService {
    Map<String, String> getCardapio() throws IOException;
}
