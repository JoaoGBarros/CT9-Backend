package org.br.ct9backend.curso.controller;

import org.br.ct9backend.curso.model.PPC;
import org.br.ct9backend.curso.service.PPCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ppc")
public class PPCController {


    @Autowired
    private PPCService ppcService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{cursoId}/cadastrarPPC")
    public ResponseEntity<PPC> cadastrarPPC(@PathVariable Long cursoId, @RequestBody PPC ppc) {
        try{
            PPC savedppc = ppcService.criarPPC(cursoId, ppc);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedppc);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{id}/getPPCs")
    public ResponseEntity<List<PPC>> getPPCs(@PathVariable Long id) {
        try{
            List<PPC> ppcs = ppcService.getPPCs(id);
            return ResponseEntity.status(HttpStatus.OK).body(ppcs);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/getPPC/{cursoId}/{ppcId}")
    public ResponseEntity<PPC> getPPC(@PathVariable Long cursoId, @PathVariable Long ppcId) {
        try{
            PPC ppc = ppcService.getPPC(cursoId, ppcId);
            return ResponseEntity.status(HttpStatus.OK).body(ppc);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/deletePPC/{ppcId}")
    public ResponseEntity<Boolean> deletePPC(@PathVariable Long ppcId) {
        try{
            ppcService.deletePPC(ppcId);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }
}
