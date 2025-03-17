package org.br.ct9backend.curso.controller;

import org.br.ct9backend.curso.model.Alocacao;
import org.br.ct9backend.curso.model.dto.AlocacaoRequestDTO;
import org.br.ct9backend.curso.model.dto.AlocacaoResponseDTO;
import org.br.ct9backend.curso.service.AlocacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alocacao")
public class AlocacaoController {

    @Autowired
    private AlocacaoService alocacaoService;

    @GetMapping("/getAlocacao/{alocacaoId}")
    public ResponseEntity<Alocacao> getAlocacao(@PathVariable Long alocacaoId) {
        try{
            Alocacao alocacao = alocacaoService.getAlocacao(alocacaoId);
            return ResponseEntity.status(HttpStatus.OK).body(alocacao);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/getAlocaoSalaHoje/{predio}/{sala}")
    public ResponseEntity<List<AlocacaoResponseDTO>> getAlocaoSalaHoje(@PathVariable String predio, @PathVariable String sala) {
        try{
            List<AlocacaoResponseDTO> alocacao = alocacaoService.getAlocaoSalaHoje(predio, sala);
            return ResponseEntity.status(HttpStatus.OK).body(alocacao);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/saveAlocacao/{disciplinaId}")
    public ResponseEntity<Alocacao> saveAlocacao(@PathVariable Long disciplinaId, @RequestBody Alocacao alocacao) {
        try{
            Alocacao saved = alocacaoService.saveAlocacao(disciplinaId, alocacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateAlocacao/{alocacaoId}")
    public ResponseEntity<Alocacao> updateAlocacao(@PathVariable Long alocacaoId, @RequestBody Alocacao alocacao) {
        try{
            Alocacao saved = alocacaoService.updateAlocacao(alocacaoId, alocacao);
            return ResponseEntity.status(HttpStatus.OK).body(saved);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteAlocacao/{alocacaoId}")
    public ResponseEntity<Boolean> deleteAlocacao(@PathVariable Long alocacaoId) {
        try{
            alocacaoService.deleteAlocacao(alocacaoId);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

}
