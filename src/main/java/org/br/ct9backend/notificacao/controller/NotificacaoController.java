package org.br.ct9backend.notificacao.controller;

import org.br.ct9backend.aluno.service.AlunoService;
import org.br.ct9backend.notificacao.model.Notificacao;
import org.br.ct9backend.notificacao.service.FirebaseMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {

    @Autowired
    private FirebaseMessagingService firebaseMessagingService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/send")
    public String sendNotification(@RequestBody Notificacao notificacao) {
        try {
            firebaseMessagingService.sendNotificationToTopic(notificacao.getTopic(), notificacao.getTitulo(), notificacao.getMensagem());
            return "Notificação enviada com sucesso!";
        } catch (IOException e) {
            return "Erro ao enviar notificação: " + e.getMessage();
        }
    }

}
