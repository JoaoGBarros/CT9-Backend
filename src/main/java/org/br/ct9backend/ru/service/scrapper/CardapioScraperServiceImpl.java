package org.br.ct9backend.ru.service.scrapper;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CardapioScraperServiceImpl implements CardapioScraperService {

    private static final String URL_CARDAPIO = "https://ru.ufes.br/cardapio";

    @Override
    public Map<String, String> getCardapio() throws IOException {
        Document doc = Jsoup.connect(URL_CARDAPIO).get();
        Elements bodyElements = doc.select("div.views-field-body");

        Map<String, String> cardapio = new HashMap<>();

        if (!bodyElements.isEmpty()) {
            cardapio.put("almoco", extrairTexto(bodyElements.get(0)));
        }

        if(bodyElements.size() > 1) {
            cardapio.put("jantar", extrairTexto(bodyElements.get(1)));
        }

        return cardapio;
    }


    private String extrairTexto(Element element) {
        StringBuilder sb = new StringBuilder();
        for (Element e : element.select("strong, p")) {
            sb.append(e.text()).append("\n");
        }
        return sb.toString().trim();
    }

}
