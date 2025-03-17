package org.br.ct9backend.ru.service;

import org.br.ct9backend.ru.model.Cardapio;
import org.br.ct9backend.ru.repository.CardapioRepository;
import org.br.ct9backend.ru.service.scrapper.CardapioScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CardapioServiceImpl implements CardapioService {

    @Autowired
    private CardapioScraperService scraperService;

    @Autowired
    private CardapioRepository repository;

    private static final Pattern IGNORED_LINES = Pattern.compile("(?i).*\\*.*");

    @Override
    public boolean verificarEAtualizarCardapio() throws IOException {
        Map<String, String> cardapioAtual = scraperService.getCardapio();
        LocalDate hoje = LocalDate.now();

        Optional<Cardapio> cardapioExistente = repository.findByData(hoje);

        if (cardapioExistente.isPresent()) {

            Cardapio existente = cardapioExistente.get();

            if(cardapioAtual.get("almoco") == null && cardapioAtual.get("jantar") == null) {
                return false;
            }

            boolean almocoIgual = cardapioAtual.get("almoco") != null && cardapioAtual.get("almoco").equals(existente.getAlmoco());
            boolean jantarIgual = cardapioAtual.get("jantar") != null && cardapioAtual.get("jantar").equals(existente.getJantar());

            if (almocoIgual && jantarIgual) {
                return false;
            } else {
                repository.delete(existente); // Deleta o cardápio antigo
            }
        }

        // Salva o novo cardápio
        Cardapio novoCardapio = new Cardapio(null, hoje, cardapioAtual.get("almoco"), cardapioAtual.get("jantar"));
        repository.save(novoCardapio);
        return true;
    }

    @Override
    public Map<String, Map<String, List<String>>> getCardapioDoDia() {

        Cardapio cardapio =  repository.findByData(LocalDate.now()).orElse(null);

        if(cardapio == null) {
            return null;
        }

        return parseCardapio(cardapio.getAlmoco(), cardapio.getJantar());
    }

    private static Map<String, Map<String, List<String>>> parseCardapio(String almoco, String jantar) {
        Map<String, Map<String, List<String>>> resultado = new HashMap<>();
        resultado.put("almoco", processarRefeicao(almoco));
        resultado.put("jantar", processarRefeicao(jantar));
        return resultado;
    }

    private static Map<String, List<String>> processarRefeicao(String refeicaoTexto) {

        if(refeicaoTexto == null) {
            return null;
        }

        Map<String, List<String>> refeicaoMap = new LinkedHashMap<>();
        List<String> linhas = Arrays.stream(refeicaoTexto.split("\n"))
                .map(String::trim)
                .filter(linha -> !linha.isEmpty())
                .toList();

        List<String> categorias = Arrays.asList(
                "Salada", "Prato Principal", "Opção", "Acompanhamento", "Guarnição", "Sobremesa"
        );

        String categoriaAtual = null;
        for (int i = 0; i < linhas.size() - 2 ; i++) {
            String linha = linhas.get(i);

            // Detecta início de uma nova categoria (linha atual e próxima são iguais)
            if (i < linhas.size() - 1 && linhas.get(i + 1).equals(linha) && categorias.contains(linha)) {
                categoriaAtual = linha;
                refeicaoMap.put(categoriaAtual, new ArrayList<>());
                i++; // Pula a próxima linha (repetição do nome da categoria)
            }
            // Adiciona itens à categoria atual
            else if (categoriaAtual != null && !linha.equals(categoriaAtual)) {
                refeicaoMap.get(categoriaAtual).add(linha);
            }
        }

        return refeicaoMap;
    }
}
