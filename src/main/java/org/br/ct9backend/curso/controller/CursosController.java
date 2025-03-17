package org.br.ct9backend.curso.controller;

import org.br.ct9backend.curso.model.Coordenacao;
import org.br.ct9backend.curso.model.Curso;
import org.br.ct9backend.curso.model.Disciplina;
import org.br.ct9backend.curso.model.PPC;
import org.br.ct9backend.curso.service.CursosService;
import org.br.ct9backend.ru.service.scheduler.CardapioScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
public class CursosController {

    @Autowired
    private CursosService cursosService;

    @Autowired
    private CardapioScheduler scheduler;

    @GetMapping("/teste")
    public ResponseEntity<String> teste() {
        scheduler.verificarEAtualizar();
        return ResponseEntity.status(HttpStatus.CREATED).body("Curso cadastrado com sucesso");

    }

    @GetMapping("/getTodosCursos")
    public ResponseEntity<Iterable<Curso>> getTodosCursos() {
        try{
            Iterable<Curso> cursosCadastrados = cursosService.getTodosCursos();
            return ResponseEntity.status(HttpStatus.OK).body(cursosCadastrados);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }

    @GetMapping("/getCurso/{id}")
    public ResponseEntity<Optional<Curso>> getCurso(@PathVariable Long id) {
        try {
            Optional<Curso> curso = cursosService.getCurso(id);
            return ResponseEntity.status(HttpStatus.OK).body(curso);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/cadastrarCurso")
    public ResponseEntity<Long> cadastrarCurso(@RequestBody Curso curso) {
        try {
            Curso savedCurso = cursosService.criarCurso(curso);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCurso.getId());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/editarCurso/{id}")
    public ResponseEntity<Long> editarCurso(@PathVariable Long id, @RequestBody Curso curso) {
        try {
            Curso savedCurso = cursosService.editarCurso(id, curso);
            return ResponseEntity.status(HttpStatus.OK).body(savedCurso.getId());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{id}/getDisciplinas")
    public ResponseEntity<List<Disciplina>> getDisciplinas(@PathVariable Long id) {
        try {
            List<Disciplina> disciplinas = cursosService.getDisciplinas(id);
            return ResponseEntity.status(HttpStatus.OK).body(disciplinas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{cursoId}/getCoordenacao")
    public ResponseEntity<List<Coordenacao>> getCoordenacao(@PathVariable Long cursoId) {
        try {
            List<Coordenacao> coordenacao = cursosService.getCoordenacao(cursoId);
            return ResponseEntity.status(HttpStatus.OK).body(coordenacao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @GetMapping("/getCoordenador/{id}")
    public ResponseEntity<Coordenacao> getCoordenador(@PathVariable Long id){
        try {
            Coordenacao coordenacao = cursosService.getCoordenador(id);
            return ResponseEntity.status(HttpStatus.OK).body(coordenacao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{cursoId}/salvarCoordenador")
    public ResponseEntity<Coordenacao> saveCoordenador(@PathVariable Long cursoId, @RequestBody Coordenacao coordenacao){
        try {
            Coordenacao saved = cursosService.saveCoordenacao(cursoId, coordenacao);
            return ResponseEntity.status(HttpStatus.OK).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/editarCoordenador/{id}")
    public ResponseEntity<Coordenacao> editarCoordenador(@PathVariable Long id, @RequestBody Coordenacao coordenacao){
        try {
            Coordenacao saved = cursosService.editCoordenacao(id, coordenacao);
            return ResponseEntity.status(HttpStatus.OK).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteCoordenador/{id}")
    public ResponseEntity<Boolean> deleteCoordenador(@PathVariable Long id){
        try {
            cursosService.deleteCoordenador(id);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

}