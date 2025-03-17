package org.br.ct9backend.curso.controller;


import org.br.ct9backend.curso.model.Alocacao;
import org.br.ct9backend.curso.model.Semestre;
import org.br.ct9backend.curso.service.AlocacaoService;
import org.br.ct9backend.curso.service.SemestreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/semestre")
public class SemestreController {

    @Autowired
    private SemestreService semestreService;
    @Autowired
    private AlocacaoService alocacaoService;


    @GetMapping("/getSemestres/{cursoId}")
    public ResponseEntity<List<Semestre>> getSemestres(@PathVariable Long cursoId) {
        try{
            List<Semestre> semestres = semestreService.getSemestres(cursoId);
            return ResponseEntity.status(HttpStatus.OK).body(semestres);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/salvarSemestre/{cursoId}")
    public ResponseEntity<Semestre> salvarSemestre(@PathVariable Long cursoId, @RequestBody Semestre semestre) {
        try{
            Semestre savedSemestres = semestreService.saveSemestre(cursoId, semestre);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSemestres);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/getSemestre/{semestreId}")
    public ResponseEntity<Semestre> getSemestre(@PathVariable Long semestreId) {
        try{
            Semestre semestre = semestreService.getSemestre(semestreId);
            return ResponseEntity.status(HttpStatus.OK).body(semestre);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
