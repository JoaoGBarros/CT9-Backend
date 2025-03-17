package org.br.ct9backend.aluno.controller;


import org.br.ct9backend.aluno.model.Aluno;
import org.br.ct9backend.aluno.model.dto.AlunoDTO;
import org.br.ct9backend.aluno.model.dto.AtualizaSituacaoRequestDTO;
import org.br.ct9backend.aluno.model.dto.PorcentagemCursoCompletoDTO;
import org.br.ct9backend.aluno.service.AlunoService;
import org.br.ct9backend.curso.model.dto.DisciplinaInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping("/cadastrarAluno")
    public ResponseEntity<AlunoDTO> cadastrarAluno(@RequestBody AlunoDTO aluno) {
        try {
            AlunoDTO savedAluno =  alunoService.criarAluno(aluno);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAluno);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{firebaseUid}")
    public ResponseEntity<AlunoDTO> buscarAluno(@PathVariable String firebaseUid) {
        try {
            AlunoDTO aluno = alunoService.buscarAluno(firebaseUid);
            return ResponseEntity.status(HttpStatus.OK).body(aluno);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/getSituacao/{firebaseUid}")
    public ResponseEntity<HashMap<String,List<DisciplinaInfoDTO>>> getDisciplinasSituacao(@PathVariable String firebaseUid) {
        try {
            HashMap<String, List<DisciplinaInfoDTO>> disciplinas = alunoService.getDisciplinasInfo(firebaseUid);
            return ResponseEntity.status(HttpStatus.OK).body(disciplinas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/getPorcentagem/{firebaseUid}")
    public ResponseEntity<PorcentagemCursoCompletoDTO> getPorcentagemCursoCompleto(@PathVariable String firebaseUid) {
        try {
            PorcentagemCursoCompletoDTO completo = alunoService.getPorcentagem(firebaseUid);
            return ResponseEntity.status(HttpStatus.OK).body(completo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/updateSituacao/{firebaseUid}")
    public ResponseEntity<String> updateSituacao(@PathVariable String firebaseUid, @RequestBody List<AtualizaSituacaoRequestDTO> aluno) {
        try {
            String updatedAluno = alunoService.updateSituacao(firebaseUid, aluno);
            return ResponseEntity.status(HttpStatus.OK).body(updatedAluno);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
