package org.br.ct9backend.agenda.controller;


import org.br.ct9backend.agenda.model.AgendaDTO;
import org.br.ct9backend.agenda.model.Tarefa;
import org.br.ct9backend.agenda.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @GetMapping("/getAulasHoje/{firebaseUid}")
    public ResponseEntity<List<AgendaDTO>> getAgenda(@PathVariable String firebaseUid) {
        try {
            List<AgendaDTO> agenda = agendaService.getDisciplinasCursando(firebaseUid);
            return ResponseEntity.status(HttpStatus.OK).body(agenda);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/getTarefas/{firebaseUid}")
    public ResponseEntity<List<Tarefa>> getTarefas(@PathVariable String firebaseUid) {
        try {
            List<Tarefa> tarefas = agendaService.getTarefas(firebaseUid);
            return ResponseEntity.status(HttpStatus.OK).body(tarefas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/salvarTarefa/{firebaseUid}")
    public ResponseEntity<Tarefa> salvarTarefa(@PathVariable String firebaseUid, @RequestBody Tarefa tarefa) {
        try {
            Tarefa savedTarefa = agendaService.saveTarefa(firebaseUid, tarefa);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTarefa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/editarTarefa/{tarefaId}")
    public ResponseEntity<Tarefa> editarTarefa(@PathVariable Long tarefaId, @RequestBody Tarefa tarefa) {
        try {
            Tarefa savedTarefa = agendaService.editarTarefa(tarefaId, tarefa);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTarefa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/deletarTarefa/{tarefaId}")
    public ResponseEntity<Boolean> deletarTarefa(@PathVariable Long tarefaId) {
        try {
            Boolean deleted = agendaService.deletarTarefa(tarefaId);
            return ResponseEntity.status(HttpStatus.OK).body(deleted);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

//    @GetMapping("/getTarefa/{tarefaId}")
//    public ResponseEntity<Tarefa> getTarefa(@PathVariable Long tarefaId) {
//        try {
//            Tarefa tarefa = agendaService.getTarefa(tarefaId);
//            return ResponseEntity.status(HttpStatus.OK).body(tarefa);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//    }


}
