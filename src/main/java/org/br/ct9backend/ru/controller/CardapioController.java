package org.br.ct9backend.ru.controller;


import org.br.ct9backend.ru.model.Cardapio;
import org.br.ct9backend.ru.service.CardapioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cardapio")
public class CardapioController {

    @Autowired
    private CardapioService cardapioService;


    @GetMapping("/getCardapioDoDia")
    public ResponseEntity<Map<String, Map<String, List<String>>>> getCardapioDoDia() {
        try {
            Map<String, Map<String, List<String>>> cardapio = cardapioService.getCardapioDoDia();
            return ResponseEntity.status(HttpStatus.OK).body(cardapio);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
