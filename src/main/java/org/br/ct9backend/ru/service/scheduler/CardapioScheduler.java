package org.br.ct9backend.ru.service.scheduler;

import org.br.ct9backend.notificacao.service.FirebaseMessagingService;
import org.br.ct9backend.ru.service.CardapioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class CardapioScheduler {

    @Autowired
    private CardapioService cardapioService;

    @Autowired
    private FirebaseMessagingService firebaseMessagingService;

    /**
     * Agendador executa de segunda a sexta, começando às 9h e rodando a cada 1 hora até 17h
     */
    @Scheduled(cron = "0 0 9-17 * * MON-FRI")
    public void verificarEAtualizar() {
        try {
            boolean atualizado = cardapioService.verificarEAtualizarCardapio();

            if(atualizado){
                firebaseMessagingService.sendNotificationToTopic("cardapio", "Cardápio atualizado", "O cardápio de hoje foi atualizado!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
