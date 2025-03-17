package org.br.ct9backend.curso.controller;


import org.br.ct9backend.agenda.model.Avaliacao;
import org.br.ct9backend.curso.model.Alocacao;
import org.br.ct9backend.curso.model.Disciplina;
import org.br.ct9backend.curso.model.DisciplinaOfertada;
import org.br.ct9backend.curso.model.PPC;
import org.br.ct9backend.curso.model.dto.AvaliarDisciplinaRequest;
import org.br.ct9backend.curso.model.dto.DisciplinaOfertadaDTO;
import org.br.ct9backend.curso.service.DisciplinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/disciplina")
public class DisciplinaController {


    @Autowired
    private DisciplinaService disciplinaService;


    @GetMapping("/{disciplinaId}")
    public ResponseEntity<Disciplina> getPPCs(@PathVariable Long disciplinaId) {
        try{
            Disciplina disciplina = disciplinaService.getDisciplina(disciplinaId);
            return ResponseEntity.status(HttpStatus.CREATED).body(disciplina);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/editar/{disciplinaId}")
    public ResponseEntity<Disciplina> editDisciplina(@PathVariable Long disciplinaId, @RequestBody Disciplina disciplina) {
        try{
            Disciplina editedDisciplina = disciplinaService.editarDisciplina(disciplinaId, disciplina);
            return ResponseEntity.status(HttpStatus.CREATED).body(editedDisciplina);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/cadastrarDisciplina/{ppcId}")
    public ResponseEntity<Disciplina> cadastrarDisciplina(@PathVariable Long ppcId, @RequestBody Disciplina disciplina) {
        try{
            Disciplina savedDisciplina = disciplinaService.saveDisciplina(ppcId, disciplina);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDisciplina);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/getDisciplinasOfertadas/{semestreId}")
    public ResponseEntity<List<DisciplinaOfertadaDTO>> getDisciplinasOfertadas(@PathVariable Long semestreId) {
        try{
            List<DisciplinaOfertadaDTO> response = new ArrayList<>();
            List<DisciplinaOfertada> disciplinas = disciplinaService.getDisciplinasOfertadas(semestreId);

            for(DisciplinaOfertada disciplina : disciplinas) {
                response.add(new DisciplinaOfertadaDTO(disciplina.getId(), disciplina.getDisciplina().getCodigo(), disciplina.getTurma(), disciplina.getAlocacoes()));
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/importDisciplinasOfertadas/{cursoId}/{semestreId}")
    public ResponseEntity<List<DisciplinaOfertada>> importDisciplinasOfertadas(@PathVariable Long semestreId, @PathVariable Long cursoId, @RequestBody List<DisciplinaOfertadaDTO> disciplinas) {
        try{
            List<DisciplinaOfertada> saved = disciplinaService.importDisciplinasOfertadas(semestreId, cursoId, disciplinas);

            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/saveDisciplinaOfertada/{cursoId}/{semestreId}")
    public ResponseEntity<DisciplinaOfertada> saveDisciplinaOfertada(@PathVariable Long semestreId, @PathVariable Long cursoId, @RequestBody DisciplinaOfertadaDTO disciplina) {
        try{
            DisciplinaOfertada saved = disciplinaService.saveDisciplinasOfertadas(semestreId, cursoId, disciplina);

            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateDisciplinaOfertada/{disciplinaId}")
    public ResponseEntity<DisciplinaOfertada> updateDisciplinaOfertada(@PathVariable Long disciplinaId, @RequestBody DisciplinaOfertadaDTO disciplina) {
        try{

            DisciplinaOfertada saved = disciplinaService.updateDisciplinaOfertada(disciplina, disciplinaId);

            return ResponseEntity.status(HttpStatus.OK).body(saved);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/getDisciplinaOfertada/{disciplinaId}")
    public ResponseEntity<DisciplinaOfertadaDTO> getDisciplinaOfertada(@PathVariable Long disciplinaId) {
        try{
            DisciplinaOfertada disciplina = disciplinaService.getDisciplinaOfertada(disciplinaId);
            DisciplinaOfertadaDTO response = new DisciplinaOfertadaDTO(disciplina.getId(), disciplina.getDisciplina().getCodigo(), disciplina.getTurma(), disciplina.getAlocacoes());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/getAlocacoes/{disciplinaId}")
    public ResponseEntity<List<Alocacao>> getAlocacao(@PathVariable Long disciplinaId) {
        try{
            List<Alocacao> alocacao = disciplinaService.getAlocacao(disciplinaId);
            return ResponseEntity.status(HttpStatus.CREATED).body(alocacao);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteDisciplina/{disciplinaId}")
    public ResponseEntity<Boolean> deleteDisciplina(@PathVariable Long disciplinaId) {
        try{
            disciplinaService.deleteDisciplina(disciplinaId);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    @PostMapping("/avaliarDisciplina/{firebaseUid}")
    public ResponseEntity<String> avaliarDisciplina(@PathVariable String firebaseUid, @RequestBody AvaliarDisciplinaRequest avaliacao) {
        try{
            disciplinaService.avaliarDisciplina(avaliacao, firebaseUid);
            return ResponseEntity.status(HttpStatus.OK).body("Avaliação realizada com sucesso!");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao avaliar disciplina: " + e.getMessage());
        }
    }

}
